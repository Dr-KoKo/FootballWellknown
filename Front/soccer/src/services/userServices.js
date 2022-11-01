import { axiosAuth } from './axios';
import { store } from "..";
import {
    TOKEN_DELETE
  } from "../modules/types.js";



// 요청 URL
export const USER_URL = "/api/v1/users";
export const AUTH_URL = "/api/v1/auth";


export const getUserInfo = async () => {
    try{
        const payload = await axiosAuth.get(`${USER_URL}`)
        return payload
    } catch(err){
        return err;
    }
};


// 로그아웃
export const logoutRequest = async () => {
    try {
        // DB에서 refresh token 삭제
      await axiosAuth.delete(`${AUTH_URL}`);
  
    } catch(err) {
  
    }
    finally {
      // 여기서 엑세스 토큰도 삭제
      store.dispatch({ type: TOKEN_DELETE });
    }
  };