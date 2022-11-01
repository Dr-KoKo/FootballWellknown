import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router";
import { useSearchParams } from "react-router-dom";
import { SET_TOKEN, SOCIAL_LOGIN } from "modules/types";
import { getUserInfo } from "../../services/userServices";
import {store} from "../..";


const SocialAuth = () => {
  const navigate = useNavigate();
  const [param, setParam] = useSearchParams();
  const accessToken = param.get("token");
  
  const dispatch = useDispatch();
  
  dispatch({
    type: SET_TOKEN,
    payload: accessToken,
  });
  

  getUserInfo().then(value =>{
    const user = {
      isLogin: true,
      nickname: value.data.nickname,
      email: value.data.email,
      point: value.data.point,
    };
    dispatch({
      type: SOCIAL_LOGIN,
      payload: user
    })
    setTimeout(() => {
      navigate("/");
    }, 1);
  }
  );
  
  

    

  return (
    <div>
      <h2>여기는 소셜 로그인이 되고 나서 오는 페이집니다.</h2>
      <p>여기서 store의 유저 state를 변경해주고 자동으로 넘어가는데 아직 백엔드 코드를 덜 짜서 자동으로 넘어가지 않게 해놨습니다.
        개발이 끝나면 자동으로 넘어가도록 하겠습니다. 추후 삭제 예정
      </p>
    </div>
  );
};

export default SocialAuth;
