import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import './Home.css';
import TeamInfo from 'pages/Team/TeamInfo';
import axios from 'axios';

import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { useDispatch } from 'react-redux';

const day = ['(일)','(월)','(화)','(수)','(목)','(금)','(토)']

const Home = () => {
  const [teamToggle, setTeamToggle] = useState(true);
  const [matchInfo, setMatchInfo] = useState([]);
  const [Loading, setLoading] =useState(true);
  const [round, setRound] = useState("7");
  const dispatch = useDispatch();


  const moveToDetail = async (matchId) => {
    await axios.get(`http://localhost:8080/api/v1/matches/match/${matchId}`)
    .then((res) => {
      console.log(res.data.result);
      const payload = res.data.result.matchVO;
      let matchStatus = "";
      if(res.data.result.matchStatus === "FIN"){
        matchStatus = "경기종료";
      }
      else if(res.data.result.matchStatus === "DELAY"){
        matchStatus = "지연";
      }
      else{
        matchStatus = "경기전";
      }
      dispatch({
        type: "SET_MATCH",
        payload,
        matchStatus,
      })
    });
    window.location.href = `/match/${matchId}/MatchPredict`;
  }

  useEffect(() => {
    axios.get(`http://localhost:8080/api/v1/matches/round?round=${round}`).then((response) => {
      setMatchInfo(response.data.result);
      setLoading(false);
    });
  }, []);

  const teamButtonClicked = () =>{
    setTeamToggle(true);
  }
  const playerButtonClicked = () =>{
    setTeamToggle(false);
  }
  return (
    <div id='homeContainer'>
      <div id='home-userRank'>
        <h1>여기에 유저 포인트 랭킹 컴포넌트를 넣을거에요~</h1>
      </div>
      <div>
      <div id='home-matchPlan'>
        <h1>{`현재 라운드: ${round}`}</h1>
        {(matchInfo.length !== 0 ? 
        <Table sx={{ minWidth: 650 }} aria-label="simple table">
          <TableHead>
            <TableRow>
            <TableCell>날짜</TableCell>
              <TableCell align="center">시간</TableCell>
              <TableCell align="center">경기</TableCell>
              <TableCell align="right">장소</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
          {matchInfo.map((data) => (
            <TableRow
            hover
            key={data.matchVO.matchId}
            onClick={() => moveToDetail(data.matchVO.matchId)}
            sx={{ '&:last-child TableCell, &:last-child th': { border: 0 } }}
          >
            {/* 날짜 */}
            <TableCell> 
              {data.matchVO.date.split('T')[0].split('-')[1]}.
              {data.matchVO.date.split('T')[0].split('-')[2]}
              {day[new Date(data.matchVO.date).getDay()]}
            </TableCell>
            {/* 시간 */}
            <TableCell>
              {data.matchVO.date.split('T')[1]}
            </TableCell>
            {/* 경기 */}
            <TableCell align="center">
              <span style={{marginRight: 10}}>{data.matchVO.home}</span>
              <img src={data.matchVO.homeImage} alt="home" width={'5%'}/>
              <span style={{marginLeft: 10, marginRight: 10}}>
                {data.matchStatus === "FIN"
                ?
                data.matchVO.homeScore + " vs " + data.matchVO.awayScore
                :
                " vs "
                }
              </span>
              <img src={data.matchVO.awayImage} alt="away" width={'5%'}/> 
              <span style={{marginLeft: 10}}>
                {data.matchVO.away}
              </span> 
            </TableCell>
            {/* 장소 */}
            <TableCell align="right">{data.matchVO.stadium}</TableCell>
            </TableRow>
          ))}
          </TableBody>
        </Table>
        : null)}
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
    </div>
  );
};

export default Home;