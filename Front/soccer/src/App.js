import React from "react";
import Home from "./Home";
import About from "./About";
import Layout from "./components/Layout";
import { Route, Routes } from "react-router";
import SocialLogin from "./components/user/SocialLogin";
import SocialAuth from "./components/user/SocialAuth";

function App() {
  return (
    <Layout>
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/about' element={<About />} />
        <Route path='/user/login' element={<SocialLogin/>} />
        <Route path='/api/v1/login/oauth2/code/*' element={<SocialAuth />} />
      </Routes>
    </Layout>
  );
}

export default App;
