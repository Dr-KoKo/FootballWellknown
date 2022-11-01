import React, { useState, useCallback, useEffect, useRef } from "react";
import { useParams } from "react-router";
import * as Stomp from "@stomp/stompjs";
import { useSelector } from "react-redux";
import { TextareaAutosize, Button, Box } from "@mui/material";

function Chatting() {
  const [messages, setMessages] = useState([]);
  const [data, setData] = useState("");
  const [connected, setConnected] = useState(false);
  const chatWindow = useRef(null);
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
    setMessages((_messages) => [..._messages, JSON.parse(message)]);
    moveScrollToReceiveMessage();
  };

  const handleChangeData = useCallback((event) => {
    setData(event.target.value);
  }, []);

  const handleSendMesssage = (e) => {
    e.preventDefault();

    if (!connected) {
      console.log("connecting");
      return;
    }

    if (!client.current.connected) {
      return;
    }

    if (data === "") {
      return;
    }

    client.current.publish({
      destination: "/pub/chat",
      body: JSON.stringify({
        type: "message",
        sender: user.nickname === "" ? "noname" : user.nickname,
        channelId: matchId,
        data,
      }),
    });

    setData("");
  };

  const moveScrollToReceiveMessage = useCallback(() => {
    if (chatWindow.current) {
      chatWindow.current.scrollTo({
        top: chatWindow.current.scrollHeight,
        behavior: "smooth",
      });
    }
  }, []);

  return (
    <div className="d-flex flex-column justify-content-center align-items-center vh-100">
      <h2>{!connected ? "연결중..." : "연결완료"}</h2>
      <div className="d-flex flex-column" style={{ width: 1000 }}>
        <div className="text-box">
          <span>{user.nickname === "" ? "noname" : user.nickname}</span> 님
          환영합니다!
        </div>
        <div ref={chatWindow}>
          {messages.map((message, index) => {
            const { sender, data } = message;
            // messages 배열을 map함수로 돌려 각 원소마다 item을 렌더링 해줍니다.
            return (
              <div key={index} className="d-flex flex-row">
                {sender && <div className="message-nickname">{sender}: </div>}
                <div>{data}</div>
              </div>
            );
          })}
        </div>
      </div>
      <div>
        <form className="card">
          <div className="d-flex align-items-center">
            <TextareaAutosize
              className="form-control chat-message"
              maxLength={400}
              autoFocus
              value={data}
              style={{ width: 200, height: 50, fontSize: 20 }}
              onChange={handleChangeData}
            />
            <Button
              type="button"
              className="btn btn-primary send-btn"
              variant="contained"
              onClick={handleSendMesssage}
              disabled={!connected}>
              전송
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
}
export default Chatting;
