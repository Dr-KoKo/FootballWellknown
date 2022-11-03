import React, { useState, useEffect, useRef, useMemo } from "react";
import axios from "axios";
import * as Stomp from "@stomp/stompjs";
import { useSelector } from "react-redux";
import "./Chat.css";
import Messages from "./Messages";
import Input from "./Input";

function Chatting() {
  const [message, setMessage] = useState("");
  const [messages, setMessages] = useState([]);
  const [connected, setConnected] = useState(false);
  const [predict, setPredict] = useState([]);
  const client = useRef({});
  const user = useSelector((state) => state.user);
  const match = useSelector((state) => state.match);
  const name = user.nickname === "" ? "익명" : user.nickname;

  useEffect(() => {
    connect();
    if (name !== "익명") {
      axios
        .get(
          `http://localhost:8080/api/v1/matches/predict/match/my/${user.email}/${match.matchId}`
        )
        .then((res) => {
          setPredict(res.data.result.whereWin);
        });
    }

    return;
  }, []);

  const connect = () => {
    client.current = new Stomp.Client({
      brokerURL: process.env.REACT_APP_LOCAL_WEBSOCKET_URL,
      reconnectDelay: 1000,
      heartbeatIncoming: 1000,
      heartbeatOutgoing: 1000,
      connectionTimeout: 0,
      debug: function (err) {
        // console.log(err);
      },
      webSocketFactory: () => {
        return new WebSocket(process.env.REACT_APP_LOCAL_WEBSOCKET_URL);
      },
      onConnect: () => {
        setTimeout(() => subscribe(), 1000);
      },
    });

    client.current.activate();
  };

  const subscribe = () => {
    client.current.subscribe("/sub/channel/" + match.matchId, (message) =>
      onMessage(message.body)
    );
    client.current.publish({
      destination: "/pub/getUser",
      body: match.matchId,
    });
    setConnected(true);
  };

  const onMessage = (message) => {
    console.log(message);
    let json = JSON.parse(message);
    if (json.type === "INFO") {
    } else {
      setMessages((_messages) => [..._messages, json]);
    }
  };

  const sendMessage = (e) => {
    e.preventDefault();

    if (!connected) {
      console.log("connecting");
      return;
    }

    if (!client.current.connected) {
      return;
    }

    if (message === "") {
      return;
    }

    client.current.publish({
      destination: "/pub/chat",
      body: JSON.stringify({
        type: "MESSAGE",
        sender: name,
        channelId: match.matchId,
        predict,
        data: message,
      }),
    });

    setMessage("");
  };

  return (
    <div className="container">
      <Messages messages={messages} match={match} />
      <Input
        message={message}
        setMessage={setMessage}
        sendMessage={sendMessage}
      />
    </div>
  );
}
export default Chatting;
