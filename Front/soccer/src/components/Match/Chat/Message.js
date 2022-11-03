import React from "react";
import { Avatar } from "@mui/material";
import "./Message.css";

import ReactEmoji from "react-emoji";

const Message = ({ message: { type, data, sender }, img }) => {
  let isSentByUser = false;

  console.log(img);

  if (type === "message") {
    isSentByUser = true;
  }

  return isSentByUser ? (
    <div className="messageContainer justifyStart">
      <div className="imgBox">
        <Avatar src={img} />
      </div>
      <div className="messageBox backgroundLight">
        <p className="messageText colorDark">{ReactEmoji.emojify(data)}</p>
      </div>
      <p className="sentText pl-10 ">{sender}</p>
    </div>
  ) : (
    <div className="messageContainer justifyEnd">
      <p className="sentText pr-10">{type}</p>
      <div className="messageBox backgroundBlue">
        <p className="messageText colorWhite">{ReactEmoji.emojify(data)}</p>
      </div>
    </div>
  );
};

export default Message;
