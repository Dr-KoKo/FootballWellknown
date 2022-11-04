import axios from "axios";
import { store } from "..";

axios.defaults.headers.post['Content-Type'] = 'application/json';

/**
 * 인증 필요없는 axios
 */

export const request = axios.create({
  baseURL: process.env.REACT_APP_SERVER_URL,
  withCredentials: true,
});

request.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    return Promise.reject(error);
  }
);

/**
 * 인증 필요한 axios
 */

export const axiosAuth = axios.create({
  baseURL: process.env.REACT_APP_SERVER_URL,
  timeout: 10000,
  withCredentials: true,
  
});

axiosAuth.interceptors.request.use(
  function (config) {
    const accessToken = store.getState().user.token;
    if (accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`;
      
    }
    return config;
  },
  function (error) { 
    return Promise.reject(error);
  }
);

axiosAuth.interceptors.response.use(
  (response) => {
    return response;
  },

  // 에러 처리
  (error) => {
    console.log("axiosAuth interceptors response 에러!!")
    console.log(error)
    return Promise.reject(error);
  }
);
