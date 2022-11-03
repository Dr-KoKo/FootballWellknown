import React, { useState, useEffect, useRef } from "react";
import axios from "axios";
import { useParams } from "react-router";
import * as Stomp from "@stomp/stompjs";
import { useSelector } from "react-redux";
import Messages from "./Messages";
import Input from "./Input";
import drawImage from "components/assets/draw.png";
import unCheckedImage from "components/assets/unchecked.png";

function Chatting() {
  const [message, setMessage] = useState("");
  const [messages, setMessages] = useState([]);
  const [connected, setConnected] = useState(false);
  const [predict, setPredict] = useState([]);
  const [avatarSrc, setAvatarSrc] = useState(unCheckedImage);
  const client = useRef({});
  const user = useSelector((state) => state.user);
  const match = useSelector((state) => state.match);
  const params = useParams();
  const matchId = params.matchId;
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

          switch (predict) {
            case "HOME":
              setAvatarSrc(match.homeImage);
              break;
            case "AWAY":
              setAvatarSrc(match.awayImage);
              break;
            case "DRAW":
              setAvatarSrc(drawImage);
              break;
            default:
          }
        });
    }

    return;
  }, []);

  const connect = () => {
    client.current = new Stomp.Client({
      brokerURL: process.env.REACT_APP_WEBSOCKET_URL,
      reconnectDelay: 1000,
      heartbeatIncoming: 1000,
      heartbeatOutgoing: 1000,
      connectionTimeout: 0,
      debug: function (err) {
        // console.log(err);
      },
      webSocketFactory: () => {
        return new WebSocket(process.env.REACT_APP_WEBSOCKET_URL);
      },
      onConnect: () => {
        setTimeout(() => subscribe(), 1000);
      },
    });

    client.current.activate();
  };

  const subscribe = () => {
    client.current.subscribe("/sub/channel/" + matchId, (message) =>
      onMessage(message.body)
    );

    setConnected(true);
  };

  const onMessage = (message) => {
    console.log(message);
    setMessages((_messages) => [..._messages, JSON.parse(message)]);
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
        type: "message",
        sender: name,
        channelId: matchId,
        data: message,
      }),
    });

    setMessage("");
  };

  return (
    <div className="container">
      <Messages messages={messages} img={avatarSrc} />
      <Input
        message={message}
        setMessage={setMessage}
        sendMessage={sendMessage}
      />
    </div>
  );
}
export default Chatting;
