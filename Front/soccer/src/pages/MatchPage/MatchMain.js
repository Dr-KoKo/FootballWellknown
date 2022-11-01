import React, { Fragment, useState } from "react";
import { useEffect } from "react";
import axios from "axios";
import { Box, Container, Tab, Typography } from "@mui/material";
import { useDispatch } from "react-redux";
import { TabContext, TabList } from "@mui/lab";
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

const day = ['(일)','(월)','(화)','(수)','(목)','(금)','(토)']

const MatchMain = () => {
  const dispatch = useDispatch();
  const currentYear = new Date().getFullYear();
  const currentMonth = new Date().getMonth() + 1;
  const [value, setValue] = useState(currentYear.toString()+currentMonth.toString());
  const [matches, setMatches] = useState(
    [{
      matchVO:{
        matchId: 0,
        date: "",
        home: "",
        homeImage: "",
        awayImage: "",
        homeScore: "",
        away:"",
        awayScore: "",
        stadium: "",
      },
      matchStatus: "",
    }]
  );

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

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
    axios
    .get(`http://localhost:8080/api/v1/matches/dates?year=${value.substring(0,4)}&month=${value.substring(4,6)}`)
    .then((res) => {
        setMatches(res.data.result);
    });
  },[value]);

  return (
    <Container>
      <TabContext value={value}>
        <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
          <TabList 
            onChange={handleChange} 
            aria-label="lab API tabs example"
            textColor="black"
            sx={{width:'100%', display:'flex', justifyContent:'space-evenly'}}
            centered
          >
            <Tab label="2022년 8월" value="202208" />
            <Tab label="9월" value="202209" />
            <Tab label="10월" value="202210" />
            <Tab label="11월" value="202211" />
            <Tab label="12월" value="202212" />
            <Tab label="2023년 1월" value="202301" />
            <Tab label="2월" value="202302" />
            <Tab label="3월" value="202303" />
            <Tab label="4월" value="202304" />
            <Tab label="5월" value="202305" />
          </TabList>
        </Box>
      </TabContext>


      <TableContainer component={Paper}>
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
            {matches.length === 0
              ? <p>예정된 경기가 없습니다</p>
              : matches.map((data, index) => (
                  <TableRow
                    hover
                    key={index}
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
      </TableContainer>
    </Container>
  );
};

export default MatchMain;
