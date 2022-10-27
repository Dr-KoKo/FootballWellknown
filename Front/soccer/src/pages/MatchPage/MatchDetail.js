import axios from 'axios';
import React, { Fragment, useEffect, useState } from 'react';
import { useParams } from 'react-router';
import { Grid, Box } from '@mui/material';

const MatchDetail = () => {
  const params = useParams();
  const matchId = params.matchId;

  const [match, setMatch] = useState({
    matchId: 0,
    date: "",
    home: "",
    homeImage: "",
    awayImage: "",
    homeScore: 0,
    away: "",
    awayScore: 0,
    stadium: ""
  });
  const [matchStatus, setMatchStatus] = useState("");

  useEffect(() => {
    axios.get(`http://localhost:8080/api/v1/matches/match/${matchId}`)
    .then((res) => {
        setMatch(res.data.result.matchVO);
        console.log(match);
        if(res.data.result.matchStatus === "FIN"){
          setMatchStatus("경기종료");
        }
        else if(res.data.result.matchStatus === "DELAY"){
          setMatchStatus("지연");
        }
        else{
          setMatchStatus("경기전");
        }
    });
  },[]);

  return (
    <Fragment>
      <Box>
        <Grid container spacing={2}>
          <Grid item xs={1}/>
          <Grid item xs={3}>
            <img src={match.homeImage} alt="no"/>
          </Grid>
          <Grid item xs={1}>
            {matchStatus === "경기종료" && <p>{match.homeScore}</p>}
          </Grid>
          <Grid item xs={2}>
            <p>{matchStatus}</p>
            <p>{match.date}</p>
            <p>{match.stadium}</p>
          </Grid>
          <Grid item xs={1}>
            {matchStatus === "경기종료" && <p>{match.awayScore}</p>}
          </Grid>
          <Grid item xs={3}>
            <img src={match.awayImage} alt="no"/>
          </Grid>
          <Grid item xs={1}/>
        </Grid>
      </Box>
      <br/>
      <Fragment>
        <span>승부예측 | </span>
        <span>라인업 | </span>
        <span>선수평가 | </span>
        <span>패들릿 | </span>
        <span>오픈톡 | </span>
      </Fragment>
    </Fragment>
  );
};

export default MatchDetail;