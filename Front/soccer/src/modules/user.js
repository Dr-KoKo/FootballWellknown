import {
  SET_TOKEN,
  SOCIAL_LOGIN,
  LOGOUT,
  UPDATE_NICKNAME,
  TOKEN_DELETE,
} from "../modules/types.js";

// 유저 초기 상태
export const initState = {
  isLogin: false,
  nickname: "",
  email: "",
  auth: "",
  token: "",
  snsType: "",
  point: 0,
  roleType: "",
};

// 유저 관련 리듀서
// 인자로 상태와 액션객체를 받는다.
export default function (state = initState, action) {
  switch (action.type) {
    case SET_TOKEN:
      return {
        ...state,
        token: action.payload,
      };
    case SOCIAL_LOGIN:
      
      return {
        ...state,
        isLogin: action.payload.isLogin,
        nickname: action.payload.nickname,
        email: action.payload.email,
        point: action.payload.point,
      };
    case LOGOUT:
      return {
        isLogin: false,
        nickname: "",
        email: "",
        auth: "",
        token: "",
        snsType: "",
        point: 0,
        roleType: "",
      };
    case UPDATE_NICKNAME:
      return {
        ...state,
        nickname: action.payload.nickname,
      };
    case TOKEN_DELETE:
      return initState;
    default:
      return state;
  }
}
