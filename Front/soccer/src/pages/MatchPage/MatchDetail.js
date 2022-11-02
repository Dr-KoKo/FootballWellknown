import React, { Fragment, useEffect, useState } from 'react';
import { Outlet,  } from 'react-router';
import { Grid, Box, Container, Typography } from '@mui/material';
import { NavLink } from 'react-router-dom';
import { store } from 'index';
import styled from 'styled-components';

const StyledNavLink = styled(NavLink)`
  color:black;
  font-size: x-large;
  font-family: Arial, Helvetica, sans-serif;
  text-decoration: none;
  margin: 10px;
  &:hover,
  &:focus{
      color: blue;
  }
  &:active{
      color: red;
  }
`;

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
      <br/>
      <Grid container spacing={2}>
        <Grid item xs={1}/>
        <Grid item xs={2}>
          <StyledNavLink to="MatchPredict">승부예측</StyledNavLink>
        </Grid>
        <Grid item xs={2}>
          <StyledNavLink to="LineUp">라인업</StyledNavLink>
        </Grid>
        <Grid item xs={2}>
          <StyledNavLink to="PlayerEvaluate">선수평가</StyledNavLink>
        </Grid>
        <Grid item xs={2}>
          <StyledNavLink to="Padlet">패들릿</StyledNavLink>
        </Grid>
        <Grid item xs={2}>
          <StyledNavLink to="Chatting">채팅</StyledNavLink>
        </Grid>
        <Grid item xs={1}/>
      </Grid>
      <br/>
      <Fragment>
        <Outlet />
      </Fragment>
    </Container>
  );
};

export default MatchDetail;