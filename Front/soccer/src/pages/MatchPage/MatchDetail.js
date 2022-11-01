import React, { Fragment, useEffect, useState } from 'react';
import { Outlet,  } from 'react-router';
import { Grid, Box, Container } from '@mui/material';
import { Link } from 'react-router-dom';
import { store } from 'index';


const MatchDetail = () => {
  const match = store.getState().match;
  const matchStatus = match.matchStatus;
  return (
    <Container>
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
    </Container>
  );
};

export default MatchDetail;