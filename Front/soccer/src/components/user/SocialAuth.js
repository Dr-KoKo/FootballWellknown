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
    <></>
  );
};

export default SocialAuth;
