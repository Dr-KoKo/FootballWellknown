import axios from "axios";
import Loading from "components/Loading";

import React, { useState, useEffect } from "react";
import { Outlet, useParams } from "react-router";
import { Link } from "react-router-dom";
import "./TeamDetail.css";
const TeamDetail = () => {

  const { id } = useParams();
  const [loading, setLoading] = useState(true);
  const [datas, setDatas] = useState({
    teamInfo: "",
    finMatch: [{ date: "" }],
    yetMatch: [],
  });
  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/v1/teams/${id}/details`)
      .then((response) => {
        console.log(response.data.result);
        setDatas(response.data.result);
        setLoading(false);
      });
  }, []);
  
  return (
    <div id="teamDet">
      {loading ? <Loading /> :<div id="teamDet">  
      <div id="teamSummary">
        <div id="image">
          <img id="realImage" src={datas.teamInfo.image} alt=""></img>
        </div>
        <table id="table">
          <thead>
            <tr>
              <th className="th2" id="rank">Rank</th>
              <th className="th2" id="result">전적</th>
              <th className="th2" id="pts">승점</th>
              <th className="th2" id="gd">득실차</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td id="rank">{datas.teamInfo.rank}</td>
              <td id="result">{datas.teamInfo.win}승 {datas.teamInfo.draw}무 {datas.teamInfo.lose}패</td>
              <td id="pts">{datas.teamInfo.pts}</td>
              <td id="gd">{datas.teamInfo.goals-datas.teamInfo.loseGoals}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div id="buttons">
        <Link to={"match"} className="btn"><div id="btns">Matches</div></Link>
        <Link to={"player"} className="btn"><div id="btns">Players</div></Link>
        <Link to={"board"} className="btn"><div id="btns">Board</div></Link>
      </div>
      <Outlet></Outlet>
    </div>
}
</div>
  );
};

export default TeamDetail;
