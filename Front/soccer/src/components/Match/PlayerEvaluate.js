import axios from 'axios';
import React, { useEffect } from 'react';
import { useParams } from 'react-router';

const PlayerEvaluate = () => {
  const params = useParams();
  const matchId = params.matchId;
  

  useEffect(()=>{
    axios.get(`http://localhost:8080/api/v1/matches/statistics/${matchId}`)
    .then(res => {
      let data = res.data.result;
      console.log(data);
    })
  },[]);
  return (
    <div>
      PlayerEvaluate
    </div>
  );
};

export default PlayerEvaluate;