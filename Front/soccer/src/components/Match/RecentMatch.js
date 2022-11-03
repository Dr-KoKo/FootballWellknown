import { Box, Grid, Typography } from '@mui/material';
import axios from 'axios';
import React, { useEffect, useState } from 'react';
import styled from 'styled-components';

const StyledBox = styled(Box)((props) => ({
  borderWidth: '1px',
  borderStyle: 'solid',
  borderColor: props.result === "win" 
    ? 'green'
    : (
      props.result === "draw"
      ? 'gray'
      : 'red'
    ),
  color: props.result === "win" 
  ? 'green'
  : (
    props.result === "draw"
    ? 'gray'
    : 'red'
  ),
  fontWeight: 'bold',
  borderRadius: 5,
  width: 'fit-content',
  height: 'fit-content',
  margin: 2
}));
const RecentMatch = (props) => {
  const [homeInfo, setHomeInfo] = useState({
    name: "",
    win: 0,
    draw: 0,
    lose : 0,
    goals: 0,
    loseGoals: 0,
    rank: 0,
  });
  const [awayInfo, setAwayInfo] = useState({
    name: "",
    win: 0,
    draw: 0,
    lose : 0,
    goals: 0,
    loseGoals: 0,
    rank: 0,
  });
  const [homeFinMatch, setHomeFinMatch] = useState([]);
  const [awayFinMatch, setAwayFinMatch] = useState([]);
  const [homeAvgScore, setHomeAvgScore] = useState(0);
  const [homeAvgLoseScore, setHomeAvgLoseScore] = useState(0);
  const [awayAvgScore, setAwayAvgScore] = useState(0);
  const [awayAvgLoseScore, setAwayAvgLoseScore] = useState(0);

  useEffect(()=>{
    axios.get(`http://localhost:8080/api/v1/teams/${props.homeId}/details`)
      .then(res=>{
        let temp = res.data.result;
        setHomeInfo(temp.teamInfo);
        setHomeFinMatch(temp.finMatch);
        let matchNums = temp.teamInfo.win + temp.teamInfo.draw + temp.teamInfo.lose;
        setHomeAvgScore((temp.teamInfo.goals / matchNums).toFixed(2));
        setHomeAvgLoseScore((temp.teamInfo.loseGoals / matchNums).toFixed(2));
      });
      axios.get(`http://localhost:8080/api/v1/teams/${props.awayId}/details`)
      .then(res=>{
        let temp = res.data.result;
        setAwayInfo(temp.teamInfo);
        setAwayFinMatch(temp.finMatch);
        let matchNums = temp.teamInfo.win + temp.teamInfo.draw + temp.teamInfo.lose;
        setAwayAvgScore((temp.teamInfo.goals / matchNums).toFixed(2));
        setAwayAvgLoseScore((temp.teamInfo.loseGoals / matchNums).toFixed(2));
      });
  }, [props]);
  return (
    <Box
      sx={{
        backgroundColor: 'whitesmoke',
        opacity: 0.7
      }}
    >
      {/* 팀명, 순위 */}
      <Grid container display={'flex'}>
        <Grid item xs={4} textAlign={'right'}>
          <Typography variant='h6' fontWeight={'bold'}>
            {homeInfo.name}
          </Typography>
          <Typography>
            {homeInfo.rank}위 {homeInfo.win}승 {homeInfo.draw}무 {homeInfo.lose}패 
          </Typography>
        </Grid>
        <Grid item xs={2} display={'flex'} justifyContent={'center'} alignItems={'center'}>
          <Typography variant='h6' fontWeight={'bold'}>
            VS
          </Typography>
        </Grid>
        <Grid item xs={4}>
          <Typography variant='h6' fontWeight={'bold'}>
            {awayInfo.name} <br/>
          </Typography>
          {awayInfo.rank}위 {awayInfo.win}승 {awayInfo.draw}무 {awayInfo.lose}패 
        </Grid>
      </Grid>
      {/* 평균 득실 */}
      <Grid container display={'flex'}>
        <Grid item xs={4} display={'flex'} justifyContent={'flex-end'} textAlign={'right'}>
          <Box sx={{
            width: 70 * homeAvgScore,
            height: 20,
            backgroundColor: 'red',
            borderRadius: 10
          }}
          >
            <Typography color={'white'} paddingRight={1}>
              {homeAvgScore}
            </Typography>
          </Box>
        </Grid>
        <Grid item xs={2} textAlign={'center'} display={'flex'} justifyContent={'center'}>
          <Typography variant='h6'>
            평균 득점
          </Typography>
        </Grid>
        <Grid item xs={4}>
          <Box sx={{
            width: 70 * awayAvgScore,
            height: 20,
            backgroundColor: 'blue',
            borderRadius: 10
          }}
          >
            <Typography color='white' paddingLeft={1}>
              {awayAvgScore}
            </Typography>
          </Box>
        </Grid>
      </Grid>
      <Grid container display={'flex'}>
        <Grid item xs={4} display={'flex'} justifyContent={'flex-end'} textAlign={'right'}>
          <Box sx={{
            width: 70 * homeAvgLoseScore,
            height: 20,
            backgroundColor: 'red',
            borderRadius: 10
          }}
          >
            <Typography color={'white'} paddingRight={1}>
              {homeAvgLoseScore}
            </Typography>
          </Box>
        </Grid>
        <Grid item xs={2} textAlign={'center'}>
          <Typography variant='h6' display={'flex'} justifyContent={'center'}>
            평균 실점
          </Typography>
        </Grid>
        <Grid item xs={4}>
          <Box sx={{
            width: 70 * awayAvgLoseScore,
            height: 20,
            backgroundColor: 'blue',
            borderRadius: 10
          }}
          >
            <Typography color='white' paddingLeft={1}>
              {awayAvgLoseScore}
            </Typography>
          </Box>
        </Grid>
      </Grid>
      {/* 최근경기 */}
      <Grid container display={'flex'}>
        <Grid item xs={4} display={'flex'} justifyContent={'flex-end'} textAlign={'right'}>
          {homeFinMatch.slice(0).reverse().map((match,index) => {
            if(match.home === homeInfo.name){
              if(match.homeScore < match.awayScore) 
                return (<StyledBox key={index} result='lose'>패</StyledBox>)
              else if(match.homeScore === match.awayScore) 
                return (<StyledBox key={index} result='draw'>무</StyledBox>)
              else 
                return (<StyledBox key={index} result='win'>승</StyledBox>)
            }else{
              if(match.homeScore < match.awayScore) 
                return (<StyledBox key={index} result='win'>승</StyledBox>)
              else if(match.homeScore === match.awayScore) 
                return (<StyledBox key={index} result='draw'>무</StyledBox>)
              else 
                return (<StyledBox key={index} result='lose'>패</StyledBox>)
            }
          })}
        </Grid>
        <Grid item xs={2} textAlign={'center'}>
          <Typography variant='h6'>
            최근 경기
          </Typography>
        </Grid>
        <Grid item xs={4} display={'flex'}>
          {awayFinMatch.map((match,index) => {
            if(match.away === awayInfo.name){
              if(match.homeScore < match.awayScore) 
                return (<StyledBox key={index} result='win'>승</StyledBox>)
              else if(match.homeScore === match.awayScore) 
                return (<StyledBox key={index} result='draw'>무</StyledBox>)
              else 
                return (<StyledBox key={index} result='lose'>패</StyledBox>)
            }else{
              if(match.homeScore < match.awayScore) 
                return (<StyledBox key={index} result='lose'>패</StyledBox>)
              else if(match.homeScore === match.awayScore) 
                return (<StyledBox key={index} result='draw'>무</StyledBox>)
              else 
                return (<StyledBox key={index} result='win'>승</StyledBox>)
            }
          })}
        </Grid>
      </Grid>

    </Box>
  );
};

export default RecentMatch;