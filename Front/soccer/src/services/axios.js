import axios from "axios";
import { store } from "..";
import { LOGOUT, SET_TOKEN } from "../modules/types";
import { getToken } from "./userService";

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
    // console.log(error);
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
  function (response) {
    return response;
  },

  // 에러 처리
  async function (error) {
    const result = error.config;

    // 로그아웃 관련 그냥 패스
    if (result.url === "/api/vi/auth/logout" && result.method === "post") {
      return Promise.reject(error);
    }

    // 401에러 -> 무슨 에러인지 확인하고 back과도 이야기해야함!!!!
    if (error.response.status === 401 && result.retry !== true) {
      result.retry = true;
      const res = await getToken();

      if (res?.data.message === "success") {
        const accessToken = res.data.result.accessToken;
        store.dispatch({ type: SET_TOKEN, payload: { accessToken } });

        // Content-Type을 기본 Request와 동일하게 만들어줌
        if (result.headers["Content-Type"] != undefined) {
          error.response.config.headers = {
            Authorization: "Bearer " + res.accessToken,
            "Content-Type": result.headers["Content-Type"],
          };
        } else {
          error.response.config.headers = {
            Authorization: "Bearer " + res.accessToken,
          };
        }
        return await axiosAuth(result);
      } else {
        // RefreshToken 재발급 실패
        alert("다시 로그인 후 시도해주세요!");
        store.dispatch({ type: LOGOUT });
        window.location.replace("/user/login");
      }
    } else if (
      result.url === "/api/v1/auth/refresh" ||
      error?.response?.data?.result === "no token" ||
      error?.response?.data?.result === "forbidden"
    ) {
      alert("다시 로그인 후 시도해주세요!");
      store.dispatch({ type: LOGOUT });
      window.location.replace("/user/login");
    }
    return Promise.reject(error);
  }
);
