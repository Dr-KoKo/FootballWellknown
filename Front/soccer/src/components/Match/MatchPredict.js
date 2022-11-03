import axios from 'axios';
import React, { Fragment, useEffect, useState } from 'react';
import Grid from '@mui/material/Grid';
import Image from 'mui-image';
import { useSelector } from 'react-redux';
import { Box, ButtonBase, Typography } from '@mui/material';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import ListItemAvatar from '@mui/material/ListItemAvatar';
import Avatar from '@mui/material/Avatar';
import drawImage from 'components/assets/draw.png'
import RecentMatch from './RecentMatch';
import './MatchPredict.css';

const MatchPredict = () => {
  const match = useSelector((state)=>state.match);
  const user = useSelector((state)=>state.user);
  const matchStatus = match.matchStatus === "경기종료" ? true : false;
  const [predicts, setPredicts] = useState([]);
  const [homeWin, setHomeWin] = useState(0);
  const [draw, setDraw] = useState(0);
  const [awayWin, setAwayWin] = useState(0);
  const [predictMatch, setPredictMatch] = useState("");
  const [homeId, setHomeId] = useState(0);
  const [awayId, setAwayId] = useState(0);

  const clickTeam = (team) => {
    setPredictMatch(team);
  }

  const submit = () => {
    if (predictMatch === ""){
      alert("팀을 선택하세요");
    }else{
      axios.post(`http://localhost:8080/api/v1/matches/predict/match`,{
        matchId: match.matchId,
        userEmail: user.email,
        userNickname: user.nickname,
        whereWin: predictMatch,
      });
      alert('예측 완료!');
      window.location.reload();
    }
  };

  useEffect(()=>{
    axios.get(`http://localhost:8080/api/v1/matches/predict/match/all/${match.matchId}`)
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
    axios.get(`http://localhost:8080/api/v1/matches/predict/match/my/${user.email}/${match.matchId}`)
    .then(res => {
      setPredictMatch(res.data.result.whereWin);
    });
    axios.get(`http://localhost:8080/api/v1/teams/name/${match.home}`)
    .then((res)=>{
      setHomeId(res.data.result.teamId);
    });
    axios.get(`http://localhost:8080/api/v1/teams/name/${match.away}`)
    .then((res)=>{
      setAwayId(res.data.result.teamId);
    });
  }, []);

  return (
    <Fragment>
      <Grid container display={'flex'}>
        <Grid item xs={4} >
          <ButtonBase
            disabled={matchStatus}
            onClick={()=>{clickTeam("HOME")}}
            sx={{
              width:'100%',
              backgroundColor: (predictMatch === "HOME") ? '#e32c22' : '#666',
              color: (predictMatch === "HOME") ? 'white' : '#9a9ea1'
            }}
          >
            <Box>
              <Image src={match.homeImage} height={60} width={60} duration={1000}/>
            </Box>
            <Box>
              <p>{match.home}</p>
              <p>{Math.round(predicts.length !== 0 ? ((homeWin / predicts.length) * 100) : 0)}%</p>
            </Box>
          </ButtonBase>
        </Grid>
        <Grid item xs={4} display={'flex'}>
          <ButtonBase
            disabled={matchStatus}
            onClick={()=>{clickTeam("DRAW")}}
            sx={{
              width:'100%',
              backgroundColor: (predictMatch === "DRAW") ? '#c379ff' : '#666',
              color: (predictMatch === "DRAW") ? 'white' : '#9a9ea1'
            }}
          >
            <Box>무승부</Box>
            <Box>{Math.round(predicts.length !== 0 ? ((draw / predicts.length) * 100) : 0)}%</Box>
          </ButtonBase>
        </Grid>
        <Grid item xs={4} display={'flex'}>
          <ButtonBase 
            disabled={matchStatus}
            onClick={()=>{clickTeam("AWAY")}}
            sx={{
              width:'100%',
              backgroundColor: (predictMatch === "AWAY") ? '#25d9bf' : '#666',
              color: (predictMatch === "AWAY") ? 'white' : '#9a9ea1'
            }}
          >
            <Box>
              <p>{match.away}</p>
              <p>{Math.round(predicts.length !== 0 ? ((awayWin / predicts.length) * 100) : 0)}%</p>
            </Box>
            <Box>
              <Image src={match.awayImage} height={60} width={60} duration={1000}/>
            </Box>
          </ButtonBase>
        </Grid>
      </Grid>
      <Box display={'flex'} justifyContent={'center'}>
        <button className='w-btn w-btn-indigo' onClick={()=>{submit()}}>예측하기</button>
      </Box>
      <Box display={'flex'} justifyContent='center'>
        <Box width={'66%'}>
          <RecentMatch homeId={homeId} awayId={awayId}/>
        </Box>
        <Box width={'33%'}>
          <List sx={{ width: '100%', bgcolor: 'background.paper', }}>
            <Typography>
              총 {predicts.length}명이 참여했습니다.
            </Typography>
            {predicts.map((predict, index) => (
              <ListItem key={index}>
                <ListItemAvatar>
                  <Avatar src={
                      predict.whereWin === "HOME" ?
                      match.homeImage : (
                        predict.whereWin === "AWAY" ?
                        match.awayImage :
                        drawImage
                      )
                    } />
              </ListItemAvatar>
              <ListItemText 
                primary={predict.userNickname} 
                secondary= {
                  predict.whereWin === "HOME" ?
                  match.home + ' 승리' : (
                    predict.whereWin === "AWAY" ?
                    match.away + ' 승리' :
                    '무승부'
                  )
                }
              />
              </ListItem>
            ))}
          </List>    
        </Box>
      </Box>
    </Fragment>
  );
};

export default MatchPredict;