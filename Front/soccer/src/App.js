import React from "react";
import Home from "./Home";
import About from "./About";
import Layout from "./components/Layout";
import { Route, Routes } from "react-router";
import TeamInfo from "./pages/Team/TeamInfo";

function App() {
  return (
    <Layout>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />
        <Route path="/teaminfo" element={<TeamInfo />} />
      </Routes>
    </Layout>
  );
}

export default App;
