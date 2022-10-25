import axios from "axios";
import React, { useState, useEffect } from "react";
import { useParams } from "react-router";

const TeamDetail = () => {
  const {id} = useParams();
  const [datas, setDatas] = useState({teamInfo : "", finMatch : [], yetMatch:[]});
  useEffect(() => {
    axios.get(`http://localhost:8080/api/v1/teams/${id}/details`).then((response) => {
        console.log(response.data.result);
      setDatas(response.data.result);
    });
  }, []);
  return (<div>
    <div> 클럽명 : {datas.teamInfo.name}</div>
    <div><img src={datas.teamInfo.image} alt=""></img></div>
    <div>승 : {datas.teamInfo.win}</div>
    <div>무 : {datas.teamInfo.draw}</div>
    <div>패 : {datas.teamInfo.lose}</div>
    <div>득점 :{datas.teamInfo.goals}</div>
    <div>실점 : {datas.teamInfo.loseGoals}</div>
    <div>순위 : {datas.teamInfo.rank}</div>
    <div>평점 : {datas.teamInfo.pts}</div>
    <div>
        {datas.finMatch.map((data) =>(
            <div key={data.matchId}>
            <div>{data.matchId}</div>
            </div>
        ))}
    </div>
  </div>);
};

export default TeamDetail;
