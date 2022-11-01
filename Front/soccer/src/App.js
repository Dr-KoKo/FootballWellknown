import React from "react";
import Home from "./Home";
import Layout from "./components/Layout";
import { Route, Routes } from "react-router";
import TeamInfo from "./pages/Team/TeamInfo";
import MatchMain from "./pages/MatchPage/MatchMain";
import TeamDetail from "pages/Team/TeamDetail";
import TeamPlayer from "pages/Team/TeamPlayer";
import TeamBoard from "pages/Team/TeamBoard";
import TeamMatches from "pages/Team/TeamMatches";
import PlayerDetail from "pages/Team/PlayerDetail";
import MatchDetail from "./pages/MatchPage/MatchDetail";
import Chatting from "components/Match/Chatting";
import LineUp from "components/Match/LineUp";
import MatchPredict from "components/Match/MatchPredict";
import Padlet from "components/Match/Padlet";
import PlayerEvaluate from "components/Match/PlayerEvaluate";
import SocialLogin from "./components/user/SocialLogin";
import SocialAuth from "./components/user/SocialAuth";
import BoardList from "./pages/BoardPage/BoardList";
import BoardDetail from "./pages/BoardPage/BoardDetail";
import BoardWrite from "./pages/BoardPage/BoardWrite";

function App() {
  return (
    <Layout>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/teaminfo" element={<TeamInfo />} />
        <Route path="/teamdetail/:id" element={<TeamDetail />}>
          <Route path="match" element={<TeamMatches />}></Route>
          <Route path="player" element={<TeamPlayer />}></Route>
          <Route path="board" element={<TeamBoard />}></Route>
        </Route>
        <Route path="/player/:id" element={<PlayerDetail />}></Route>
        <Route path="/match" element={<MatchMain />} />
        <Route path="/match/:matchId" element={<MatchDetail />}>
          <Route path="Chatting" element={<Chatting />} />
          <Route path="LineUp" element={<LineUp />} />
          <Route path="MatchPredict" element={<MatchPredict />} />
          <Route path="Padlet" element={<Padlet />} />
          <Route path="PlayerEvaluate" element={<PlayerEvaluate />} />
        </Route>
        <Route path="/" element={<Home />} />
        <Route path="/user/login" element={<SocialLogin />} />
        <Route path="/oauth/redirect" element={<SocialAuth />} />

        <Route path="/board" element={<BoardList />}></Route>
        <Route path="/board/detail/:id" element={<BoardDetail />} />
        <Route path="/board/write" element={<BoardWrite />} />
      </Routes>
    </Layout>
  );
}

export default App;
