import React from "react";
import Home from "./Home";
import Layout from "./components/Layout";
import { Route, Routes } from "react-router";
import TeamInfo from "./pages/Team/TeamInfo";
import MatchMain from './pages/MatchPage/MatchMain';

function App() {
  return (
    <Layout>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/teaminfo" element={<TeamInfo />} />
        <Route path="/match" element={<MatchMain/>}/>
      </Routes>
    </Layout>
  );
}

export default App;
