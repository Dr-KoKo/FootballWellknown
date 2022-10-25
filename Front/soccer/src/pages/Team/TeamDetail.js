import axios from "axios";
import React, { useState, useEffect } from "react";
import { useParams } from "react-router";

const TeamDetail = () => {
  const {id} = useParams();
  const [datas, setDatas] = useState(null);
  useEffect(() => {
    axios.get(`http://localhost:8080/api/v1/teams/${id}/details`).then((response) => {
      setDatas(response.data.result);
    });
  }, []);
  return (<div>
    <div>{datas.teamInfo.name}</div>
    <div><img src={datas.teamInfo.image} alt=""></img></div>
    <div>{datas.teamInfo.win}</div>
    <div>{datas.teamInfo.draw}</div>
    <div>{datas.teamInfo.lose}</div>
    <div>{datas.teamInfo.goals}</div>
    <div>{datas.teamInfo.loseGoals}</div>
    <div>{datas.teamInfo.rank}</div>
    <div>{datas.teamInfo.pts}</div>
  </div>);
};

export default TeamDetail;
