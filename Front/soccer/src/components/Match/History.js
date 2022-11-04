import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import Timeline from '@mui/lab/Timeline';
import TimelineItem from '@mui/lab/TimelineItem';
import TimelineSeparator from '@mui/lab/TimelineSeparator';
import TimelineContent from '@mui/lab/TimelineContent';
import TimelineOppositeContent from '@mui/lab/TimelineOppositeContent';
import TimelineDot from '@mui/lab/TimelineDot';
import { Avatar, Box, Container, Typography } from '@mui/material';
import { deepOrange } from '@mui/material/colors';
import HistoryContent from './HistoryContent';

const History = () => {
  const SERVER_URL = process.env.REACT_APP_SERVER_URL;

  const match = useSelector((state)=>state.match);
  const [histories, setHistories] = useState([]);
  useEffect(() => {
    axios.get(SERVER_URL+`/api/v1/matches/history/${match.matchId}`)
    .then(res => {
      setHistories(res.data.result);
    })
  },[]);

  return (
    <Container sx={{
      backgroundColor: 'white',
      borderRadius: 10
    }}>
      {histories.length === 0
      ?
      <Box
        sx={{
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          minHeight: 300
        }}
      >
        경기 시작 전입니다.
      </Box>
      :
      <Timeline position="alternate">
        {histories.map((history,index) => {
          if((history.teamType === "HOME" && index % 2 === 0) ||
          (history.teamType === "AWAY" && index % 2 === 1)) return(
            <TimelineItem>
              <TimelineOppositeContent>
                <HistoryContent history = {history} opposite={true}/>
              </TimelineOppositeContent>
              <TimelineSeparator>
                <TimelineDot>
                  <Avatar sx={{ bgcolor: deepOrange[500], fontSize: 16 }}>
                    {history.time}
                  </Avatar>
                </TimelineDot>
              </TimelineSeparator>
              <TimelineContent sx={{ py: '12px', px: 2 }}>
              </TimelineContent>
            </TimelineItem>
          )
          else return (
            <TimelineItem>
              <TimelineSeparator>
                <TimelineDot>
                  <Avatar sx={{ bgcolor: deepOrange[500], fontSize: 16 }}>
                    {history.time}
                  </Avatar>
                </TimelineDot>
              </TimelineSeparator>
              <TimelineContent sx={{ py: '12px', px: 2 }}>
                <HistoryContent history = {history} opposite = {false}/>
              </TimelineContent>
            </TimelineItem>
          )
        })}
      </Timeline>
      }
    </Container>
  );
};

export default History;