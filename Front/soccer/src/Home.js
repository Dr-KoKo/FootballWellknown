import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import './Home.css';
import TeamInfo from 'pages/Team/TeamInfo';
import axios from 'axios';

import Carousel from 'react-material-ui-carousel'
import { IconButton, Paper } from '@mui/material';
import ArrowRightIcon from '@mui/icons-material/ArrowRight';
import ArrowLeftIcon from '@mui/icons-material/ArrowLeft';

import { useDispatch } from 'react-redux';
import Item from 'Item';
import HorizonLine from 'components/HorizonLine';
import GoalCard from 'components/player_card/GoalCard';
import AssistCard from 'components/player_card/AssistCard'
import UserRank from 'components/user_rank/UserRank';

import Grid from '@mui/material/Grid';
import { getMatchInfo, getPlayerInfo } from 'services/homeService';
import { getDailyRank, getWeeklyRank } from 'services/userServices';



const Home = () => {
  const [teamToggle, setTeamToggle] = useState(true);
  const [matchInfo, setMatchInfo] = useState([]);
  const [round, setRound] = useState(15);
  const [loading, setLoading] = useState(true)
  const dispatch = useDispatch();
  const [carousel, setCarousel] = useState([]);
  const [mostGoal, setMostGoal] =useState([]);
  const [mostAssist, setMostAssist] =useState([]);
  const [dailyRank, setDailyRank] = useState([]);
  const [weeklyRank, setWeeklyRank] = useState([]);

  const loadDailyRank = async () => {
      const result = await getDailyRank();
      if(result?.data?.message === '성공'){
          setDailyRank(result.data.responseRankingDTOList);
        }
  }

  const loadWeeklyRank = async () => {
      const result = await getWeeklyRank();
      if(result?.data?.message === '성공'){
          setWeeklyRank(result.data.responseRankingDTOList);
        }
  }

  const getMatches = async (round) => {
    const result = await getMatchInfo(round);
    if(result?.data?.message === 'Success'){
      setMatchInfo(result.data.result);
      setLoading(false)
      const len = result.data.result.length;
      setCarousel([[result.data.result.slice(0,len/2)], [result.data.result.slice(len/2,len)]]);
    }
  }

  const getPlayers = async () => {
    const result = await getPlayerInfo();
    if(result?.data?.message === 'Success'){
      setMostGoal(result.data.result.scorers);
      setMostAssist(result.data.result.assisters);
    }
    
  }
  

  useEffect(() => {
    getMatches(round);
    getPlayers();
    loadDailyRank();
    loadWeeklyRank();
  }, [round]);

  
  const teamButtonClicked = () =>{
    setTeamToggle(true);
  }
  const playerButtonClicked = () =>{
    setTeamToggle(false);
  }
  const leftButtonClicked = () =>{
    if(round - 1 >= 1){
      setRound(round-1);
    } 
  }
  const rightButtonClicked = () =>{
    if(round + 1 <= 38){
      setRound(round+1);
    } 
  }
  
  
  return (
    <div id='homeContainer'>
      <div id='home-userRank'>
        <Grid container spacing={1}>
          <Grid item xs={6}>
            <div>
              <h2>일간 포인트 랭킹</h2>
              <div className='home-userRankInfo'>
                  <UserRank list={dailyRank}/>
              </div>
            </div>
          </Grid>
          <Grid item xs={6}>
            <div>
              <h2>주간 포인트 랭킹</h2>
              <div className='home-userRankInfo'>
                <UserRank list={weeklyRank}/>
              </div>
            </div>
          </Grid>
        </Grid>
      </div>
     
      <div id='home-matchPlan'>
        <div className='home-round'>
          <IconButton onClick={leftButtonClicked}>
            <ArrowLeftIcon fontSize='large'/>
          </IconButton>
          <h1>{`현재 라운드: ${round}`}</h1>
          <IconButton onClick={rightButtonClicked}>
            <ArrowRightIcon fontSize='large'/>
          </IconButton>
        </div>
        
        {carousel.length !== 0 && carousel[0][0].length !== 0 ? 
        <Carousel 
          sx={{ minWidth:"80%"}}
        >
            {
                carousel.map( (item, i) => <Item key={i} item={item} /> )
            }
        </Carousel> : <h2>아직 예정된 경기가 없습니다.</h2>}
      </div>
      
      <div id='home-rank'>
        <div style={{marginBottom: "0.5rem"}}>
          <button className='home-categoryButton' id={teamToggle ? "curr-home-categoryButton" : null} onClick={teamButtonClicked}>팀 순위</button>
          <span style={{fontSize:"1.5rem"}}>|</span>
          <button className='home-categoryButton' id={teamToggle ? null : "curr-home-categoryButton"} onClick={playerButtonClicked}>개인 순위</button>
        </div>
          {teamToggle ? 
            <TeamInfo count={10}></TeamInfo> 
            : 
            <div className='home-playerRank'>
              <GoalCard category="최다 득점" players={mostGoal}></GoalCard>
              <AssistCard category="최다 도움" players={mostAssist}></AssistCard>
            </div>
          }
      </div>
    </div>
  );
};


export default Home;
