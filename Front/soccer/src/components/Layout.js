import React, { Fragment } from 'react';
import Logo from './assets/logo.png';

import { Link, NavLink } from 'react-router-dom';
import './Layout.css';
const Layout = (props) => {
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
            <li  id='li'><NavLink className='navbar_li' to="/"><div>COMMUNITY</div></NavLink></li>            
          </ul>
        </nav>
        <main id='background'>{props.children}</main>
    </Fragment>
  );
};

export default Layout;