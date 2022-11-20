import React, { useEffect, useState } from "react";
import { Avatar } from "@mui/material";
import ReactEmoji from "react-emoji";
import drawImage from "components/assets/draw.png";
import unCheckedImage from "components/assets/unchecked.png";
import "./Message.css";

const Message = ({ message: { type, data, sender, predict }, match }) => {
  const [avatarSrc, setAvatarSrc] = useState(unCheckedImage);
  let isSentByUser = false;

  useEffect(() => {
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
        break;
    }
  }, []);

  if (type === "MESSAGE") {
    isSentByUser = true;
  }

  return isSentByUser ? (
    <div className="messageContainer justifyStart">
      <div className="imgBox">
        <Avatar src={avatarSrc} sx={{ backgroundColor: "dark" }} />
      </div>
      <div className="sizeController">
        <div className="sizeController messageBox backgroundLight">
          <p className="messageText colorDark">{ReactEmoji.emojify(data)}</p>
        </div>
        <p className="sentText pl-10 ">{sender}</p>
      </div>
    </div>
  ) : (
    <div className="messageContainer justifyEnd">
      <div className="messageBox backgroundBlue">
        <p className="messageText colorWhite">{ReactEmoji.emojify(data)}</p>
      </div>
    </div>
  );
};

export default Message;
