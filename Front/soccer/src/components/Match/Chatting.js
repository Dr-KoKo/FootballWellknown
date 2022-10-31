import React, { useState, useEffect } from "react";
import { useParams } from "react-router";
import * as Stomp from "stompjs";
import { useSelector } from "react-redux";

function Chatting() {
  const [chatMessages, setChatMessages] = useState([]);
  const [data, setData] = useState("");
  const user = useSelector((state) => state.user);
  const params = useParams();
  const matchId = params.matchId;

  const stompClient = Stomp.over(
    new WebSocket("ws://localhost:8080/api/v1/ws")
  );

  useEffect(() => {
    console.log(user);
    stompClient.debug = null;
    stompClient.connect(
      {},
      function (frame) {
        stompClient.subscribe("/sub/channel/" + matchId, (message) =>
          onMessage(message.body)
        );
      },
      function (err) {
        console.log(err);
      }
    );

    return;
  }, []);

  const onMessage = (message) => {
    setChatMessages((_chatMessages) => [..._chatMessages, JSON.parse(message)]);
  };

  const sendMessage = (e) => {
    e.preventDefault();
    stompClient.send(
      "/pub/chat",
      {},
      JSON.stringify({
        type: "message",
        sender: user.nickname === "" ? "noname" : user.nickname,
        channelId: matchId,
        data,
      })
    );
  };

  return (
    <div>
      <div>
        {chatMessages && chatMessages.length > 0 && (
          <ul>
            {chatMessages.map((_chatMessage, index) => (
              <li key={index}>
                {_chatMessage.sender}: {_chatMessage.data}
              </li>
            ))}
          </ul>
        )}
      </div>
      <div>
        <form>
          <div>
            <label>Data: </label>
            <input
              type="text"
              id="data"
              onChange={(e) => setData(e.target.value)}
            />
          </div>
          <button onClick={sendMessage}>Send</button>
        </form>
      </div>
    </div>
  );
}
export default Chatting;
