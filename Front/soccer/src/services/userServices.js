import { axiosAuth, request } from './axios';
import { store } from "..";
import {
    TOKEN_DELETE
  } from "../modules/types.js";



// 요청 URL
export const USER_URL = "/api/v1/users";
export const USER_RANKING_URL = "/api/v1/users/ranks";
export const USER_PREDICT_URL = "/api/v1/users/predicts"
export const AUTH_URL = "/api/v1/auth";
export const USER_RANK_URL = "/api/v1/ranking";
export const USER_BOARD_URL = "/api/v1/users/boards"
export const USER_POINT_URL = "/api/v1/users/points"

export const getDailyRank = async () => {
  try{
    const payload = await request.get(`${USER_RANK_URL}/daily`)
    return payload;
  }
  catch(err){
    return err;
  }
}

export const getWeeklyRank = async () => {
  try{
    const payload = await request.get(`${USER_RANK_URL}/weekly`)
    return payload;
  }
  catch(err){
    return err;
  }
}


export const getUserInfo = async () => {
    try{
        const payload = await axiosAuth.get(`${USER_URL}`)
        return payload
    } catch(err){
        return err;
    }
};

export const getUserRank = async () => {
  try{
    const payload = await axiosAuth.get(`${USER_RANKING_URL}`)
    return payload
  } catch(err){
    return err;
  }
}

export const getUserBoard = async (currentPage) => {
  try{
    const payload = await axiosAuth.get(`${USER_BOARD_URL}/${currentPage}`)
    return payload
  } catch(err){
    return err;
  }
}

export const getUserPredict = async () => {
  try{
    const payload = await axiosAuth.get(`${USER_PREDICT_URL}`)
    return payload
  } catch(err){
    return err
  }
}

export const getUserPoint = async (currentPage) => {
  try{
    const payload = await axiosAuth.get(`${USER_POINT_URL}/${currentPage}`)
    return payload
  } catch(err){
    return err
  }
}

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

  // 토큰 재발급
export const getToken = async () => {
  try {
    const payload = await request.get(`${AUTH_URL}/refresh`);
    return payload;
  } catch (err) {
    return err;
  }
};

export const updateNickname = async (user) => {
  try{
    await request.post(`${USER_URL}/nickname`, user);
  } catch(err) {
    return err;
  }
}