import React, { useState, useEffect, useRef, useCallback } from "react";
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
  const [predict, setPredict] = useState("");
  const client = useRef({});
  const user = useSelector((state) => state.user);
  const match = useSelector((state) => state.match);
  const name = user.nickname === "" ? "익명" : user.nickname;
  const connectMessage = {
    type: "Connect",
    data: "연결중입니다...",
    sender: "System",
  };

  useEffect(() => {
    setMessages((_messages) => [..._messages, connectMessage]);
    connect();
    if (name !== "익명") {
      axios
        .get(
          process.env.REACT_APP_SERVER_URL +
            `/api/v1/matches/predict/match/my/${user.email}/${match.matchId}`
        )
        .then((res) => {
          setPredict(res.data.result.whereWin);
        });
    }

    return;
  }, []);

  const connect = () => {
    client.current = new Stomp.Client({
      //      brokerURL: "ws://localhost:8080/api/v1/ws",
      brokerURL: process.env.REACT_APP_WEBSOCKET_URL,
      reconnectDelay: 1000,
      heartbeatIncoming: 1000,
      heartbeatOutgoing: 1000,
      connectionTimeout: 0,
      debug: function (err) {
        // console.log(err);
      },
      webSocketFactory: () => {
        // return new WebSocket("ws://localhost:8080/api/v1/ws");
        return new WebSocket(process.env.REACT_APP_WEBSOCKET_URL);
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

    setConnected(true);

    connectMessage.data = "연결되었습니다.";
  };

  const onMessage = (message) => {
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

    let json = JSON.stringify({
      type: "MESSAGE",
      sender: name,
      channelId: match.matchId,
      predict,
      data: message,
    });

    client.current.publish({
      destination: "/pub/chat",
      body: json,
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
