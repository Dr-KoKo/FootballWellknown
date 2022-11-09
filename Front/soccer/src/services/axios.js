import axios from "axios";
import { store } from "..";
import { getToken } from "./userServices";
import { SET_TOKEN} from "modules/types";
import { useDispatch } from "react-redux";


axios.defaults.headers.post['Content-Type'] = 'application/json';


/**
 * 인증 필요없는 axios
 */

export const request = axios.create({
  baseURL: process.env.REACT_APP_LOCAL_SERVER_URL,
  // baseURL: process.env.REACT_APP_SERVER_URL,
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
  baseURL: process.env.REACT_APP_LOCAL_SERVER_URL,
  // baseURL: process.env.REACT_APP_SERVER_URL,
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
  // 에러처리
  async function(error) {
    const result = error.config;
    console.log(error)
    console.log(error.response.status)
    if(error.response.status === 401){
      const res = await getToken();
      const accessToken = res.data.body.token;
      store.dispatch({ type: SET_TOKEN, payload:  accessToken  })
      location.reload();
      
      

    }
    return Promise.reject(error)


  }

  // // 에러 처리
  // (error) => {
  //   console.log(error.config)
  //   console.log("axiosAuth interceptors response 에러!!")
  //   console.log(error)
  //   return Promise.reject(error);
  // }
);
