import axios from 'axios';
import React, { Fragment, useEffect, useState } from 'react';
import { useParams } from 'react-router';
import Grid from '@mui/material/Grid';

const MatchPredict = () => {
  console.log(124364);
  const params = useParams();
  const matchId = params.matchId;
  const [predicts, setPredicts] = useState([]);
  const [homeWin, setHomeWin] = useState(0);
  const [draw, setDraw] = useState(0);
  const [awayWin, setAwayWin] = useState(0);

  useEffect(()=>{
    axios.get(`http://localhost:8080/api/v1/matches/predict/match/all/${matchId}`)
    .then(res => {
      let data = res.data.result;
      setPredicts(res.data.result);
      let homeWinCnt = 0;
      let drawCnt = 0;
      let awayCnt = 0;
      data.map((predict) => {
        if(predict.whereWin === "HOME") homeWinCnt++;
        if(predict.whereWin === "DRAW") drawCnt++;
        if(predict.whereWin === "AWAY") awayCnt++;
      });
      setHomeWin(homeWinCnt);
      setDraw(drawCnt);
      setAwayWin(awayCnt);
    });
  }, []);

  return (
    <Fragment>
      <Fragment>
        총 {predicts.length}명이 참여했습니다.
      </Fragment>
      <br/>
      <Grid container>
        <Grid item xs={4}>
          <p>본머스</p>
          <p>{predicts.length !== 0 ? ((homeWin / predicts.length) * 100) : 0}%</p>
        </Grid>
        <Grid item xs={4}>
          <p>무승부</p>
          <p>{predicts.length !== 0 ? ((draw / predicts.length) * 100) : 0}%</p>
        </Grid>
        <Grid item xs={4}>
          <p>본머스</p>
          <p>{predicts.length !== 0 ? ((awayWin / predicts.length) * 100) : 0}%</p>
        </Grid>
      </Grid>
      <Fragment>
        <ul>
          {predicts.map((predict) => (
            <li>
              {predict.userNickname}
              {predict.whereWin === "HOME" && "홈팀 승리"}
              {predict.whereWin === "DRAW" && "무승부"}
              {predict.whereWin === "AWAY" && "원정팀 승리"}
            </li>
          ))}
        </ul>
      </Fragment>
    </Fragment>
  );
};

export default MatchPredict;