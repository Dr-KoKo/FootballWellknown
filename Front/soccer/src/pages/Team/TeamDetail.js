import axios from "axios";
import Loading from "components/Loading";

import React, { useState, useEffect } from "react";
import { useParams } from "react-router";
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
      {loading ? <Loading /> : <div id="teamDet">  
      <div id="teamSummary">
        <div id="image">
          <img id="realImage" src={datas.teamInfo.image} alt=""></img>
        </div>
        <table id="table">
          <thead>
            <tr>
              <th id="rank">Rank</th>
              <th id="result">전적</th>
              <th id="pts">승점</th>
              <th id="gd">득실차</th>
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
        <button className="btn">Matches</button>
        <button className="btn">Players</button>
        <button className="btn">Board</button>
      </div>
      <div id="matches">
        <div>최근 매치</div>
        {datas.finMatch.map((data) => (
          <div key={data.matchId} id="finMatch">
            <div id="matchInfo">
              <div id="matchDate">{data.date.substring(0, 10)} {data.date.substring(11, 16)}</div>
              <div id="matchStadium">{data.stadium}</div>
            </div>            
            <div id="finMatchData">           
            <div className="team">
              <img src={data.homeImage} alt=""></img>
              <div>{data.home}</div>
            </div>
            <div id="scoreVS">
            <div className="score">{data.homeScore}</div>
            <div className="VS">VS</div>
            <div className="score">{data.awayScore}</div>
            </div>
            <div className="team">
              <img src={data.awayImage} alt=""></img>
              <div>{data.away}</div>
            </div>
          </div>
          </div>
        ))}
      </div>
      <div id="matches">
        <div>예정 매치</div>
        {datas.yetMatch.map((data) => (
          <div key={data.matchId} id="finMatch">
            <div id="matchInfo">
              <div id="matchDate">{data.date.substring(0, 10)} {data.date.substring(11, 16)}</div>
              <div id="matchStadium">{data.stadium}</div>
            </div>   
            <div id="finMatchData">
           
            <div className="team">
              <img src={data.homeImage} alt=""></img>
              <div>{data.home}</div>
            </div>
            <div className="VS2">VS</div>
            <div className="team">
              <img src={data.awayImage} alt=""></img>
              <div>{data.away}</div>
            </div>
          </div>
          </div>
        ))}
      </div>
    </div>
    }
    </div>
  );
};

export default TeamDetail;
