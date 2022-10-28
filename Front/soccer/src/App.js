import React from "react";
import Home from "./Home";
import Layout from "./components/Layout";
import { Route, Routes } from "react-router";
import TeamInfo from "./pages/Team/TeamInfo";
import MatchMain from "./pages/MatchPage/MatchMain";
import TeamDetail from "pages/Team/TeamDetail";
import MatchDetail from './pages/MatchPage/MatchDetail';
import Chatting from "components/Match/Chatting";
import LineUp from "components/Match/LineUp";
import MatchPredict from "components/Match/MatchPredict";
import Padlet from "components/Match/Padlet";
import PlayerEvaluate from "components/Match/PlayerEvaluate";

function App() {
  return (
    <Layout>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/teaminfo" element={<TeamInfo />} />
        <Route path="/match" element={<MatchMain/>}/>
        <Route path="/match/:matchId" element={<MatchDetail/>}>
        <Route path="/teamdetail/:id" element={<TeamDetail/>} />
          <Route path="Chatting" element={<Chatting/>}/>
          <Route path="LineUp" element={<LineUp/>}/>
          <Route path="MatchPredict" element={<MatchPredict/>}/>
          <Route path="Padlet" element={<Padlet/>}/>
          <Route path="PlayerEvaluate" element={<PlayerEvaluate/>}/>
        </Route>
      </Routes>
    </Layout>
  );
}

export default App;
