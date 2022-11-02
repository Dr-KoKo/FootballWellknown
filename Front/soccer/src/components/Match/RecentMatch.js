import axios from 'axios';
import React, { useEffect, useState } from 'react';

const RecentMatch = (props) => {
  const [homeInfo, setHomeInfo] = useState({
    win: 0,
    draw: 0,
    lose : 0,
    goals: 0,
    loseGoals: 0,
    rank: 0,
  });
  const [awayInfo, setAwayInfo] = useState({
    win: 0,
    draw: 0,
    lose : 0,
    goals: 0,
    loseGoals: 0,
    rank: 0,
  });
  const [homeFinMatch, setHomeFinMatch] = useState([]);
  const [awayFinMatch, setAwayFinMatch] = useState([]);

  useEffect(()=>{
    axios.get(`http://localhost:8080/api/v1/teams/${props.homeId}/details`)
      .then(res=>{
        setHomeInfo(res.data.result.teamInfo);
        setHomeFinMatch(res.data.result.finMatch);
        console.log('home');
        console.log(homeInfo);
      });
    axios.get(`http://localhost:8080/api/v1/teams/${props.awayId}/details`)
      .then(res=>{
        setAwayInfo(res.data.result.teamInfo);
        setAwayFinMatch(res.data.result.finMatch);
        console.log('away');
        console.log(awayInfo);
      });
  }, [props]);
  return (
    <div>
    </div>
  );
};

export default RecentMatch;