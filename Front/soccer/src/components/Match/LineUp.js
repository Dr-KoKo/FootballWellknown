import axios from 'axios';
import React, { Fragment, useEffect, useState } from 'react';
import { Grid, Avatar, Box, Container, Typography } from '@mui/material';
import { deepOrange, deepPurple } from '@mui/material/colors';
import { useSelector } from 'react-redux';
import image from 'components/assets/groundTemplate.png'

const LineUp = () => {
  const SERVER_URL = process.env.REACT_APP_SERVER_URL;

  const match = useSelector((state)=>state.match);
  const [homeFormation, setHomeFormation] = useState([]);
  const [homeMain, setHomeMain] = useState([["",0],["",0],["",0],["",0],[]]);

  const [awayFormation, setAwayFormation] = useState([]);
  const [awayMain, setAwayMain] = useState([["",0],["",0],["",0],["",0],[]]);

  let homeCnt = 2;
  let awayCnt = 2;

  useEffect(()=>{
    axios.get(`${SERVER_URL}/api/v1/matches/${match.matchId}/lineUps`)
    .then((res)=>{
      let data = res.data.result;
      let hf = [];
      let hm = new Array(12);
      let af = [];
      let am = new Array(12);

      if(data[0].formation !== ""){
        data.map((d) => {
          let lineups = d.lineUpList
          if(d.teamType === "HOME"){
            hf = d.formation.split("-")
            hf = hf.map(Number);
            lineups.map(player => {
              if(player.position === 0){
              }else{
                let names = player.name.split(" ");
                let name = ""
                if (names.length === 1) name = names[0];
                else if(names.length === 2) name = names[1];
                else name = names[1] + " " + names[2];
                hm[player.position] = [name, player.number];
              }
            });
            let temp = [[hm[1][0],hm[1][1]]]; //골키퍼 미리 넣어두기
            let cnt = 2;
            hf.map((num) => {
              temp.push(hm.slice(cnt,cnt+num));
              cnt += num;
            });
            hm = temp;
          }else{
            af = d.formation.split("-")
            af = af.map(Number);
            lineups.map(player => {
              if(player.position === 0){
              }else{
                let names = player.name.split(" ");
                let name = ""
                if (names.length === 1) name = names[0];
                else if(names.length === 2) name = names[1];
                else name = names[1] + " " + names[2];
                am[player.position] = [name, player.number];
              }
            });
            am.reverse();
            let temp = [];
            let cnt = 0;
            let afReverse = af.reverse();
            afReverse.map((num) => {
              temp.push(am.slice(cnt,cnt+num));
              cnt += num;
            });
            temp.push([[am[10][0],am[10][1]]]);
            am = temp;
          }
        });
        setHomeFormation(hf);
        setHomeMain(hm);
        setAwayFormation(af);
        setAwayMain(am);
      }
    });
  }, []);
  return (
    <Container sx={{
      display:'flex',
      justifyContent:'center',
      backgroundColor:'white',
      borderRadius: 10
    }}
    >
      {homeFormation.length === 0
      ?
      <Box sx={{
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        minHeight: 300
      }}
      >
        출전 선수 명단 확정 전입니다
      </Box>
      :
      (
        <Box
          width={'50%'}
          height={'200%'}
          justifyContent={'center'}
          sx={{
              backgroundImage: `url(${image})`,
              backgroundRepeat: 'no-repeat',
              backgroundSize: '100% 100%',
              color: 'white'
            }}
        >
          {/* 홈 */}
          <Grid container>
            <Grid item xs={12} display="flex" justifyContent="center" alignItems="center" flexDirection={'column'} p={1.5}>
              <Avatar sx={{  width:32, height:32, bgcolor: deepOrange[500] }}>{homeMain[0][1]}</Avatar>
                <Typography fontWeight={'bold'}>
                  {homeMain[0][0]}
                </Typography>
            </Grid>
            {homeMain[1].map((player,index) => (
              <Grid item xs={12/homeMain[1].length} key={index} display="flex" justifyContent="center" alignItems="center" flexDirection={'column'} p={1.5}>
                <Avatar sx={{  width:32, height:32, bgcolor: deepOrange[500] }}>{player[1]}</Avatar>
                <Typography fontWeight={'bold'}>
                  {player[0]}
                </Typography>
              </Grid>
            ))}
            {homeMain[2].map((player,index) => (
              <Grid item xs={12/homeMain[2].length} key={index} display="flex" justifyContent="center" alignItems="center" flexDirection={'column'} p={1.5}>
                <Avatar sx={{  width:32, height:32, bgcolor: deepOrange[500] }}>{player[1]}</Avatar>
                <Typography fontWeight={'bold'}>
                  {player[0]}
                </Typography>
              </Grid>
            ))}
            {homeMain[3].map((player,index) => (
              <Grid item xs={12/homeMain[3].length} key={index} display="flex" justifyContent="center" alignItems="center" flexDirection={'column'} p={1.5}>
                <Avatar sx={{  width:32, height:32, bgcolor: deepOrange[500] }}>{player[1]}</Avatar>
                <Typography fontWeight={'bold'}>
                  {player[0]}
                </Typography>
              </Grid>
            ))}
            {homeMain.length > 4 && homeMain[4].map((player,index) => (
              <Grid item xs={12/homeMain[4].length} key={index} display="flex" justifyContent="center" alignItems="center" flexDirection={'column'} p={1.5}>
                <Avatar sx={{  width:32, height:32, bgcolor: deepOrange[500] }}>{player[1]}</Avatar>
                <Typography fontWeight={'bold'}>
                  {player[0]}
                </Typography>
              </Grid>
            ))}
          </Grid>
          {/* 어웨이 */}
          <Grid container>
            {awayMain[0].map((player,index) => (
              <Grid item xs={12/awayMain[0].length} key={index} display="flex" justifyContent="center" alignItems="center" flexDirection={'column'} p={1.5}>
                <Avatar sx={{  width:32, height:32, bgcolor: deepPurple[500] }}>{player[1]}</Avatar>
                <Typography fontWeight={'bold'}>
                  {player[0]}
                </Typography>
              </Grid>
            ))}
            {awayMain[1].map((player,index) => (
              <Grid item xs={12/awayMain[1].length} key={index} display="flex" justifyContent="center" alignItems="center" flexDirection={'column'} p={1.5}>
                <Avatar sx={{  width:32, height:32, bgcolor: deepPurple[500] }}>{player[1]}</Avatar>
                <Typography fontWeight={'bold'}>
                  {player[0]}
                </Typography>
              </Grid>
            ))}
            {awayMain[2].map((player,index) => (
              <Grid item xs={12/awayMain[2].length} key={index} display="flex" justifyContent="center" alignItems="center" flexDirection={'column'} p={1.5}>
                <Avatar sx={{  width:32, height:32, bgcolor: deepPurple[500] }}>{player[1]}</Avatar>
                <Typography fontWeight={'bold'}>
                  {player[0]}
                </Typography>
              </Grid>
            ))}
            {awayMain[3].map((player,index) => (
              <Grid item xs={12/awayMain[3].length} key={index} display="flex" justifyContent="center" alignItems="center" flexDirection={'column'} p={1.5}>
                <Avatar sx={{  width:32, height:32, bgcolor: deepPurple[500] }}>{player[1]}</Avatar>
                <Typography fontWeight={'bold'}>
                  {player[0]}
                </Typography>
              </Grid>
            ))}
            {awayMain.length > 4 && awayMain[4].map((player,index) => (
              <Grid item xs={12/awayMain[4].length} key={index} display="flex" justifyContent="center" alignItems="center" flexDirection={'column'} p={1.5}>
                <Avatar sx={{  width:32, height:32, bgcolor: deepPurple[500] }}>{player[1]}</Avatar>
                <Typography fontWeight={'bold'}>
                  {player[0]}
                </Typography>
              </Grid>
            ))}
          </Grid>
        </Box>
        )
      }
    </Container>
  );
};

export default LineUp;