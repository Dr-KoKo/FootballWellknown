import React, { Fragment } from 'react';
import Logo from './assets/logo.png';
import { Link, NavLink, useNavigate } from 'react-router-dom';
import './Layout.css';
import { useDispatch, useSelector } from 'react-redux';
import { logoutRequest } from 'services/userServices';
import { LOGOUT } from "../modules/types";
import swal from 'sweetalert';

const Layout = (props) => {
  const state = useSelector(state => state);
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const onLogoutHandler = () => {
    swal({
      title: "로그아웃 하시겠습니까?",
      icon: "warning",
      buttons: true,
      dangerMode: true,
    })
    .then((willLogout) => {
      if (willLogout) {
        swal("로그아웃 되었습니다.", {
          icon: "success",
        })
        logoutRequest();
        // 엑세스 토큰 제외하고 로그아웃
        dispatch({
          type: LOGOUT,
        });
        navigate("/");
      }});
    
  };

  
  return (
    <Fragment>
      <nav className='navbar'>
        <Link to="/">
          <img id='logo' src={Logo} alt=''></img>
          </Link>
          <ul  id='ul'>
            <li  id='li'><NavLink className='navbar_li' to="/"><div>HOME</div></NavLink></li>
            <li  id='li'><NavLink className='navbar_li' to="teaminfo"><div>TEAM</div></NavLink></li>
            <li  id='li'><NavLink className='navbar_li' to="match"><div>MATCH</div></NavLink></li>
            <li  id='li'><NavLink className='navbar_li' to="board"><div>COMMUNITY</div></NavLink></li>
            {(state.user.isLogin ? 
              <li  id='li'>
                <button id='logoutBtn' onClick={onLogoutHandler}>LOGOUT</button></li> 
              : 
              <li  id='li'><NavLink className='navbar_li' to="/user/login"><div>LOGIN</div></NavLink></li> 
              )}
                 
          </ul>
        </nav>
        <main id='background'>{props.children}</main>
    </Fragment>
  );
};

export default Layout;