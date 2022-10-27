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
function App() {
  const currentMonth = new Date().getMonth();
  return (
    <Layout>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/teaminfo" element={<TeamInfo />} />
        <Route path="/match" element={<MatchMain month={currentMonth} />} />
        <Route path="/teamdetail/:id" element={<TeamDetail/>}>
          <Route path="match" element={<TeamMatches/>}></Route>
          <Route path="player" element={<TeamPlayer/>}></Route>
          <Route path="board" element={<TeamBoard/>}></Route>
        </Route>
      </Routes>
    </Layout>
  );
}

export default App;
