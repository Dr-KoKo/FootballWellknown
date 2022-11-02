import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Tabs, Tab, Box, Typography } from '@mui/material';
import PlayerStatistics from './PlayerStatistics';
import { useSelector } from 'react-redux';
import '../Match/PlayerEvaluate.css';
function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box sx={{ p: 3 }}>
          <div>{children}</div>
        </Box>
      )}
    </div>
  );
}

function a11yProps(index) {
  return {
    id: `simple-tab-${index}`,
    'aria-controls': `simple-tabpanel-${index}`,
  };
}

const PlayerEvaluate = () => {

  const match = useSelector((state)=>state.match);

  const [home,setHome] = useState([]);
  const [away,setAway] = useState([]);

  const [team,setTeam] = useState(0);
  const handleChange = (event, index) => {
    setTeam(index);
  }

  useEffect(()=>{
    axios.get(`http://localhost:8080/api/v1/matches/statistics/${match.matchId}`)
    .then(res => {
      let data = res.data.result;
      let h = [];
      let a = [];
      data.map(players => {
        if(players.team === "HOME" && players.pass > 0){
          h.push(players);
        }
        else if(players.team === "AWAY" && players.pass > 0){
          a.push(players);
        }
      });
      setHome(h);
      setAway(a);
    })
  },[]);
  return (
    <div>
    <div id='highFr'>hi</div>
    <Box sx={{ width: '100%' }}>
      <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
        <Tabs value={team} onChange={handleChange} aria-label="basic tabs example">
          <Tab label="HOME" {...a11yProps(0)} />
          <Tab label="AWAY" {...a11yProps(1)} />
        </Tabs>
      </Box>
      <TabPanel value={team} index={0}>
        <PlayerStatistics team={home} />
      </TabPanel>
      <TabPanel value={team} index={1}>
        <PlayerStatistics team={away} />
      </TabPanel>
    </Box>
    </div>
  );
};

export default PlayerEvaluate;