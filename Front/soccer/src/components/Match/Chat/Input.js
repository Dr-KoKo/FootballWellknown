import React, { useRef, useEffect } from "react";
import buttonImg from "components/assets/button.png";
import "./Input.css";

function Input({ setMessage, sendMessage, message }) {
  const input = useRef(null);

  useEffect(() => {
    if (input.current) {
      // 할당한 DOM 요소가 불러지면 (마운트 되면)
      input.current.focus(); // focus 할당!
    }
  }, []);

  return (
    <form className="form">
      <input
        className="input"
        type="text"
        placeholder="전송하려는 메세지를 입력하세요."
        value={message}
        onChange={({ target: { value } }) => setMessage(value)}
        onKeyPress={(event) =>
          event.key === "Enter" ? sendMessage(event) : null
        }
        ref={input}
      />
      <button className="sendButton" onClick={(e) => sendMessage(e)}>
        <img className="buttonImg" src={buttonImg} />
      </button>
    </form>
  );
}

export default Input;
