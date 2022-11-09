
import HorizonLine from 'components/HorizonLine';
import React from 'react';
import './SocialLogin.css'


const SocialLogin = () => {
  const base_uri = process.env.REACT_APP_LOCAL_SERVER_URL + "/api/v1/oauth2/authorization/"
  const redirect_uri = "?redirect_uri="+ process.env.REACT_APP_LOCAL_CLIENT_URL +"/oauth/redirect";

  // const base_uri = process.env.REACT_APP_SERVER_URL + "/api/v1/oauth2/authorization/"
  // const redirect_uri = "?redirect_uri="+ process.env.REACT_APP_CLIENT_URL +"/oauth/redirect";
  const google_uri = base_uri + 'google' + redirect_uri;
  const kakao_uri = base_uri + "kakao" + redirect_uri;

  return (
    <div id='socialLogin'>
      <div id="socialLoginHead">
        <h1>회원가입하기</h1>
        <p>소셜 아이디를 이용해서 회원가입 및 로그인을 할 수 있습니다.</p>
        <HorizonLine/>
      </div>
        
      <div className="social-login-form"> 
        <a href={google_uri}>
          <img src='../assets/login_button/google_button.png' className="social-login-button" alt="구글 로그인 버튼" />
        </a>
        <a href={kakao_uri}>
          <img src='../assets/login_button/kakao_button.png' className="social-login-button" alt="카카오 로그인 버튼" />
        </a>
      </div>
    </div>
  );
};

export default SocialLogin;
