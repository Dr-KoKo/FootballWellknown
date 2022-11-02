import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import './Home.css';
import TeamInfo from 'pages/Team/TeamInfo';
import axios from 'axios';

import Carousel from 'react-material-ui-carousel'
import { IconButton } from '@mui/material';
import ArrowRightIcon from '@mui/icons-material/ArrowRight';
import ArrowLeftIcon from '@mui/icons-material/ArrowLeft';

import { useDispatch } from 'react-redux';
import Item from 'Item';
import HorizonLine from 'components/HorizonLine';



const Home = () => {
  const [teamToggle, setTeamToggle] = useState(true);
  const [matchInfo, setMatchInfo] = useState([]);
  const [round, setRound] = useState(9);
  const [loading, setLoading] = useState(true)
  const dispatch = useDispatch();
  const [carousel, setCarousel] = useState([]);
  

  const item = [
    {
        name: "Random Name #1",
        description: "Probably the most random thing you have ever seen!"
    },
    {
        name: "Random Name #2",
        description: "Hello World!"
    }
];




  useEffect(() => {
    axios.get(`http://localhost:8080/api/v1/matches/round?round=${round}`).then((response) => {
      setMatchInfo(response.data.result);
      setLoading(false);
      const len = response.data.result.length;
      setCarousel([[response.data.result.slice(0,len/2)], [response.data.result.slice(len/2,len)]]);
    });
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
        <h1>여기에 유저 포인트 랭킹 컴포넌트를 넣을거에요~</h1>
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
          {teamToggle ? <TeamInfo count={10}></TeamInfo> : <div>안녕</div>}
      </div>
    </div>
  );
};


export default Home;
