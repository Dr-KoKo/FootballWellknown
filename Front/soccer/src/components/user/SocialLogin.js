import React from 'react';


const SocialLogin = () => {
  const base_uri = process.env.REACT_APP_SERVER_URL + "/api/v1/oauth2/authorization/"
  const redirect_uri = "?redirect_uri="+ process.env.REACT_APP_CLIENT_URL +"/oauth/redirect";
  const google_uri = base_uri + 'google' + redirect_uri;
  const kakao_uri = base_uri + "kakao" + redirect_uri;

  return (
    <div>  
        <h2>소셜 로그인 테스트 페이지에 오신 걸 환영합니다.</h2>
        <a href={google_uri}>
          <img src='../assets/login_button/google_button.png' className="social-login-button" alt="구글 로그인 버튼" />
        </a>
        <a href={kakao_uri}>
          <img src='../assets/login_button/kakao_button.png' className="social-login-button" alt="카카오 로그인 버튼" />
        </a>
    </div>
  );
};

export default SocialLogin;
