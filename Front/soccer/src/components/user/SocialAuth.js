import React from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router";
import { useSearchParams } from "react-router-dom";
import { SOCIAL_LOGIN } from "modules/types";

const SocialAuth = () => {
  const [param, setParam] = useSearchParams();
  const token = param.get("token");
  const email = param.get("email");
  const nickname = param.get("nickName");
  const snsType = param.get("snsType");
  const point = param.get("point");

  const dispatch = useDispatch();
  const user = {
    isLogin: true,
    email: email,
    nickname: nickname,
    auth: "1",
    token: token,
    snsType: snsType,
    point: point,
  };
  dispatch({
    type: SOCIAL_LOGIN,
    payload: user,
  });

    // const navigate = useNavigate();
    // setTimeout(() => {
    //   navigate("/");
    // }, 1);

  return (
    <div>
      <h2>여기는 소셜 로그인이 되고 나서 오는 페이집니다.</h2>
      <p>여기서 store의 유저 state를 변경해주고 자동으로 넘어가는데 아직 백엔드 코드를 덜 짜서 자동으로 넘어가지 않게 해놨습니다.
        개발이 끝나면 자동으로 넘어가도록 하겠습니다.
      </p>
    </div>
  );
};

export default SocialAuth;
