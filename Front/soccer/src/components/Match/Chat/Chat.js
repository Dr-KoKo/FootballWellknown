import React, { useState, useCallback, useEffect, useRef } from "react";
import { useParams } from "react-router";
import * as Stomp from "@stomp/stompjs";
import { useSelector } from "react-redux";
import { TextareaAutosize, Button, Box } from "@mui/material";
import Messages from "./Messages";
import Input from "./Input";

function Chatting() {
  const [message, setMessage] = useState("");
  const [messages, setMessages] = useState([]);
  const [connected, setConnected] = useState(false);
  const client = useRef({});
  const user = useSelector((state) => state.user);
  const params = useParams();
  const matchId = params.matchId;

  useEffect(() => {
    connect();
    return;
  }, []);

  const connect = () => {
    client.current = new Stomp.Client({
      brokerURL: "ws://localhost:8080/api/v1/ws",
      reconnectDelay: 1000,
      heartbeatIncoming: 1000,
      heartbeatOutgoing: 1000,
      connectionTimeout: 0,
      debug: function (err) {
        // console.log(err);
      },
      webSocketFactory: () => {
        return new WebSocket("ws://localhost:8080/api/v1/ws");
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
        sender: user.nickname === "" ? "noname" : user.nickname,
        channelId: matchId,
        data: message,
      }),
    });

    setMessage("");
  };

  return (
    <div className="container">
      <Messages messages={messages} name={user.nickname} />
      <Input
        message={message}
        setMessage={setMessage}
        sendMessage={sendMessage}
      />
    </div>
  );
}
export default Chatting;
