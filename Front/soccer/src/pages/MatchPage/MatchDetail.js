import React, { Fragment } from 'react';
import { Outlet,  } from 'react-router';
import { Grid, Box, Container, Typography } from '@mui/material';
import { NavLink } from 'react-router-dom';
import { store } from 'index';
import './MatchDetail.css';

const MatchDetail = () => {
  const match = store.getState().match;
  const matchStatus = match.matchStatus;
  return (
    <Container>
      <Box sx={{
        backgroundColor:'white',
        borderRadius: '10px',
        opacity: 0.8,
        textAlign: 'center'
        }}
      >
        <Grid container spacing={2}>
          <Grid item xs={1}/>
          <Grid item xs={3}>
            <img src={match.homeImage} alt="no"/>
          </Grid>
          <Grid item xs={1} display={'flex'} alignItems={'center'} p={2}>
            <Typography variant='h2'>
             {matchStatus === "경기종료" && match.homeScore}
            </Typography>
          </Grid>
          <Grid item xs={2} p={2}>
            <Box 
              sx={{
                backgroundColor:'#294588', 
                color:'white', 
                borderRadius:'10px',
                margin: '10px'
              }}
            >
              {matchStatus}
            </Box>
            <Box>
              {match.date.split('T')[0].split('-')[1]}.
              {match.date.split('T')[0].split('-')[2]}
            </Box>
            <p>{match.date.split('T')[1]}</p>
            <p>{match.stadium}</p>
          </Grid>
          <Grid item xs={1} display={'flex'} alignItems={'center'} p={2}>
            <Typography  variant='h2'>
              {matchStatus === "경기종료" && match.awayScore}
            </Typography>
          </Grid>
          <Grid item xs={3}>
            <img src={match.awayImage} alt="no"/>
          </Grid>
          <Grid item xs={1}/>
        </Grid>
      </Box>
      {/* 
        MatchPage내의 navNavLink
      */}
      <Box className='nav-link-container'>
        <Grid container spacing={2}>
          <Grid item xs={2}>
            <NavLink to="MatchPredict" className={'nav-link'}>승부예측</NavLink>
          </Grid>
          <Grid item xs={2}>
            <NavLink to="LineUp" className={'nav-link'}>라인업</NavLink>
          </Grid>
          <Grid item xs={2}>
            <NavLink to="History" className={'nav-link'}>중계</NavLink>
          </Grid>
          <Grid item xs={2}>
            <NavLink to="PlayerEvaluate" className={'nav-link'}>선수평가</NavLink>
          </Grid>
          <Grid item xs={2}>
            <NavLink to="Padlet" className={'nav-link'}>패들릿</NavLink>
          </Grid>
          <Grid item xs={2}>
            <NavLink to="Chatting" className={'nav-link'}>채팅</NavLink>
          </Grid>
        </Grid>
      </Box>
      <br/>
      <Fragment>
        <Outlet />
      </Fragment>
    </Container>
  );
};

export default MatchDetail;