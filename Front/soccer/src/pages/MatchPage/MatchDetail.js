import axios from 'axios';
import React, { Fragment, useEffect, useState } from 'react';
import { Outlet, useParams } from 'react-router';
import { Grid, Box } from '@mui/material';
import { Link } from 'react-router-dom';

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
        <Link to="MatchPredict">승부예측 | </Link>
        <Link to="LineUp">라인업 | </Link>
        <Link to="PlayerEvaluate">선수평가 | </Link>
        <Link to="Padlet">패들릿 | </Link>
        <Link to="Chatting">채팅 | </Link>
      </Fragment>
      <br/>
      <Fragment>
        <Outlet />
      </Fragment>
    </Fragment>
  );
};

export default MatchDetail;