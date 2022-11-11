import axios from "axios";
import React, { useEffect, useState } from "react";
import { Tabs, Tab, Box, Typography } from "@mui/material";
import PlayerStatistics from "./PlayerStatistics";
import { useSelector } from "react-redux";
import Loading from "components/Loading";
import "../Match/PlayerEvaluate.css";
function TabPanel(props) {
  const { children, value, index, ...other } = props;
  
  return (
    <Box
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box sx={{ p: 3 }}>
          <Box>{children}</Box>
        </Box>
      )}
    </Box>
  );
}

function a11yProps(index) {
  return {
    id: `simple-tab-${index}`,
    "aria-controls": `simple-tabpanel-${index}`,
  };
}

const PlayerEvaluate = () => {
  const SERVER_URL = process.env.REACT_APP_SERVER_URL;

  const match = useSelector((state) => state.match);
  const [loading, setLoading] = useState(true);
  const [homeStat, setHomeStat] = useState({
    name: "1",
    image: "",
    shot: 0,
    shotOn: 0,
    foul: 0,
    corner: 0,
    offside: 0,
    penalty: 0,
    possession: "",
    formation: "",
    yellow: 0,
    red: 0,
    save: 0,
    pass: 0,
    passOn: 0,
  });
  const [awayStat, setAwayStat] = useState({
    name: "1",
    image: "",
    shot: 0,
    shotOn: 0,
    foul: 0,
    corner: 0,
    offside: 0,
    penalty: 0,
    possession: "",
    formation: "",
    yellow: 0,
    red: 0,
    save: 0,
    pass: 0,
    passOn: 0,
  });
  const [home, setHome] = useState([]);
  const [away, setAway] = useState([]);

  const [team, setTeam] = useState(0);
  const handleChange = (event, index) => {
    setTeam(index);
  };

  useEffect(() => {
    axios
      .get(`${SERVER_URL}/api/v1/matches/statistics/${match.matchId}`)
      .then((res) => {
        let data = res.data.result;
        let h = [];
        let a = [];
        setHomeStat(data.homeDet !== null ? data.homeDet : homeStat);
        setAwayStat(data.awayDet !== null ? data.awayDet : awayStat);
        data.players.map((players) => {
          if (players.team === "HOME" && players.pass > 0) {
            h.push(players);
          } else if (players.team === "AWAY" && players.pass > 0) {
            a.push(players);
          }
        });
        setLoading(false);
        setHome(h);
        setAway(a);
      });
  }, []);
  return (
    <Box>
      {loading? <Loading /> :<Box>
      {homeStat.possession===null?
    <Box
        sx={{
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          minHeight: 300,
          backgroundColor: 'white',
          borderRadius: 10
        }}
    >경기 시작 전입니다.</Box>:
    <Box>
      <Box id="highFr">통계</Box>
      <Box className="stat">
        <Box className="stat-left">
          <Box className="statColorLeft" sx={{ width: homeStat.possession }}></Box>
          <Box className="valLeft">{homeStat.possession}</Box>
        </Box>
        <Box className="txt">점유율</Box>
        <Box className="stat-right">
          <Box className="statColorRight" sx={{ width: awayStat.possession }}></Box>
          <Box className="valRight">{awayStat.possession}</Box>
        </Box>
      </Box>
      <Box className="stat">
        <Box className="stat-left">
          <Box className="statColorLeft" sx={{ width: `${homeStat.shot/20*100}%` }}></Box>
          <Box className="valLeft">{homeStat.shot}</Box>
        </Box>
        <Box className="txt">슈팅</Box>
        <Box className="stat-right">
          <Box className="statColorRight" sx={{ width: `${awayStat.shot/20*100}%` }}></Box>
          <Box className="valRight">{awayStat.shot}</Box>
        </Box>
      </Box>
      <Box className="stat">
        <Box className="stat-left">
          <Box className="statColorLeft" sx={{ width: `${homeStat.shotOn/20*100}%` }}></Box>
          <Box className="valLeft">{homeStat.shotOn}</Box>
        </Box>
        <Box className="txt">유효슈팅</Box>
        <Box className="stat-right">
          <Box className="statColorRight" sx={{ width: `${awayStat.shotOn/20*100}%` }}></Box>
          <Box className="valRight">{awayStat.shotOn}</Box>
        </Box>
      </Box>
      <Box className="stat">
        <Box className="stat-left">
          <Box className="statColorLeft" sx={{ width: `${homeStat.pass/(homeStat.pass+awayStat.pass)*100}%` }}></Box>
          <Box className="valLeft">{homeStat.pass}</Box>
        </Box>
        <Box className="txt">패스시도</Box>
        <Box className="stat-right">
          <Box className="statColorRight" sx={{ width: `${awayStat.pass/(homeStat.pass+awayStat.pass)*100}%` }}></Box>
          <Box className="valRight">{awayStat.pass}</Box>
        </Box>
      </Box>
      <Box className="stat">
        <Box className="stat-left">
          <Box className="statColorLeft" sx={{ width: `${homeStat.passOn/homeStat.pass*100}%` }}></Box>
          <Box className="valLeft">
            { homeStat.pass === 0 ?
            0 :
            Math.round(homeStat.passOn/homeStat.pass*100)}%
          </Box>
        </Box>
        <Box className="txt">패스성공률</Box>
        <Box className="stat-right">
          <Box className="statColorRight" sx={{ width: `${awayStat.passOn/awayStat.pass*100}%` }}></Box>
          <Box className="valRight">
            { awayStat.pass === 0 ?
            0 :
            Math.round(awayStat.passOn/awayStat.pass*100)}%
          </Box>
        </Box>
      </Box>
      <Box className="stat">
        <Box className="stat-left">
          <Box className="statColorLeft" sx={{ width: `${homeStat.corner/20*100}%` }}></Box>
          <Box className="valLeft">{homeStat.corner}</Box>
        </Box>
        <Box className="txt">코너킥</Box>
        <Box className="stat-right">
          <Box className="statColorRight" sx={{ width: `${awayStat.corner/20*100}%` }}></Box>
          <Box className="valRight">{awayStat.corner}</Box>
        </Box>
      </Box>
      <Box className="stat">
        <Box className="stat-left">
          <Box className="statColorLeft" sx={{ width: `${homeStat.penalty/20*100}%` }}></Box>
          <Box className="valLeft">{homeStat.penalty}</Box>
        </Box>
        <Box className="txt">패널티킥</Box>
        <Box className="stat-right">
          <Box className="statColorRight" sx={{ width: `${awayStat.penalty/20*100}%` }}></Box>
          <Box className="valRight">{awayStat.penalty}</Box>
        </Box>
      </Box>
      <Box className="stat">
        <Box className="stat-left">
          <Box className="statColorLeft" sx={{ width: `${homeStat.foul/20*100}%` }}></Box>
          <Box className="valLeft">{homeStat.foul}</Box>
        </Box>
        <Box className="txt">파울</Box>
        <Box className="stat-right">
          <Box className="statColorRight" sx={{ width: `${awayStat.foul/20*100}%` }}></Box>
          <Box className="valRight">{awayStat.foul}</Box>
        </Box>
      </Box>
      <Box className="stat">
        <Box className="stat-left">
          <Box className="statColorLeft" sx={{ width: `${homeStat.yellow/20*100}%` }}></Box>
          <Box className="valLeft">{homeStat.yellow}</Box>
        </Box>
        <Box className="txt">경고</Box>
        <Box className="stat-right">
          <Box className="statColorRight" sx={{ width: `${awayStat.yellow/20*100}%` }}></Box>
          <Box className="valRight">{awayStat.yellow}</Box>
        </Box>
      </Box>
      <Box className="stat">
        <Box className="stat-left">
          <Box className="statColorLeft" sx={{ width: `${homeStat.red/20*100}%` }}></Box>
          <Box className="valLeft">{homeStat.red}</Box>
        </Box>
        <Box className="txt">퇴장</Box>
        <Box className="stat-right">
          <Box className="statColorRight" sx={{ width: `${awayStat.red/20*100}%` }}></Box>
          <Box className="valRight">{awayStat.red}</Box>
        </Box>
      </Box>
      <Box className="stat">
        <Box className="stat-left">
          <Box className="statColorLeft" sx={{ width: `${homeStat.save/20*100}%` }}></Box>
          <Box className="valLeft">{homeStat.save}</Box>
        </Box>
        <Box className="txt">세이브</Box>
        <Box className="stat-right">
          <Box className="statColorRight" sx={{ width: `${awayStat.save/20*100}%` }}></Box>
          <Box className="valRight">{awayStat.save}</Box>
        </Box>
      </Box>
      <Box className="stat">
        <Box className="stat-left">
          <Box className="statColorLeft" sx={{ width: `${homeStat.offside/20*100}%` }}></Box>
          <Box className="valLeft">{homeStat.offside}</Box>
        </Box>
        <Box className="txt">오프사이드</Box>
        <Box className="stat-right">
          <Box className="statColorRight" sx={{ width: `${awayStat.offside/20*100}%` }}></Box>
          <Box className="valRight">{awayStat.offside}</Box>
        </Box>
      </Box>
      <Box sx={{ width: "100%" , marginTop:"5%", overflow:"auto"}}>
        <Box sx={{ borderBottom: 1, borderColor: "Boxider" }}>
          <Tabs
            value={team}
            onChange={handleChange}
            aria-label="basic tabs example"
          >
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
    </Box>}</Box>}
    </Box>
  );
};

export default PlayerEvaluate;
