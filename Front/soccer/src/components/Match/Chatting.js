import React, { useState, useEffect } from "react";
import { useParams } from "react-router";
import * as StompJs from "stompjs";
import * as SockJS from "sockjs-client";

let sockJS;
let stompClient;

function Chatting() {
  const [chatMessages, setChatMessages] = useState([]);
  const [sender, setSender] = useState("");
  const [data, setData] = useState("");
  const params = useParams();
  const matchId = params.matchId;

  useEffect(() => {
    sockJS = new SockJS("http://localhost:8080/api/v1/ws");
    stompClient = StompJs.over(sockJS);
    stompClient.debug = (err) => {};
    stompClient.connect(
      {},
      () => {
        stompClient.subscribe("/sub/channel/" + matchId, (message) =>
          onMessage(JSON.parse(message.body))
        );
      },
      (err) => {
        console.log(err);
      }
    );
    return function cleanup() {
      stompClient.disconnect();
    };
  }, [matchId]);

  const onMessage = (message) => {
    console.log(message);
    setChatMessages((_chatMessages) => [..._chatMessages, message]);
  };

  const sendMessage = (e) => {
    e.preventDefault();

    if (!stompClient.connected) {
      console.log("websocket is not connected");
      return;
    }

    stompClient.send(
      "/pub/chat",
      {},
      JSON.stringify({ type: "", sender, channelId: matchId, data })
    );
  };

  return (
    <div>
      <div>
        <div>
          <form>
            <div>
              <label>Sender: </label>
              <input
                type="text"
                id="name"
                onChange={(e) => setSender(e.target.value)}
              />
            </div>
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
      </div>
    </div>
  );
}
export default Chatting;
