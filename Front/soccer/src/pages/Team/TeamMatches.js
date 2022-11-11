import axios from 'axios';
import React, { useEffect, useState } from 'react';
import Loading from "components/Loading";
import { useParams } from 'react-router';
import Epl from '../../components/assets/epl.png';
import { useDispatch } from "react-redux";
import "./TeamMatches.css";

const TeamMatches = () => {
    const SERVER_URL = process.env.REACT_APP_SERVER_URL; 
    const {id} = useParams();
    const [loading, setLoading] = useState(true);
    const [recent, setRecent] = useState(true);
    const dispatch = useDispatch();

    const setHomeId = async(home) => {
      console.log(home);
      await axios.post(`${SERVER_URL}/api/v1/teams/name`,home)
      .then((res)=>{
        console.log(res.data.result.teamId);
        dispatch({
          type: "SET_HOME_ID",
          payload: res.data.result.teamId
        });
      });
    };
    const setAwayId = async(away) => {
      console.log(away);
      await axios.post(`${SERVER_URL}/api/v1/teams/name`,away)
      .then((res)=>{
        console.log(res.data.result.teamId);
        dispatch({
          type: "SET_AWAY_ID",
          payload: res.data.result.teamId
        });
      });
    };

    async function goMatchDetail(matchId) {
      let homeId = 0;
      let awayId = 0;
      await axios.get(`${SERVER_URL}/api/v1/matches/match/${matchId}`)
      .then((res) => {
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
        });
        homeId = payload.home;
        awayId = payload.away;
      });
      await setHomeId(homeId);
      await setAwayId(awayId);
      window.location.href = `/match/${matchId}/MatchPredict`;
    }
    const [datas, setDatas] = useState({
        teamInfo: "",
        finMatch: [{ date: "" }],
        yetMatch: [],
      });
      useEffect(() => {
        axios
          .get(`${SERVER_URL}/api/v1/teams/${id}/details`)
          .then((response) => {
            console.log(response.data.result);
            setDatas(response.data.result);
            setLoading(false);
          });
      }, [recent]);
    return (        
        <div id="frames">
            {loading ? <Loading /> : <div>
              <div id='matchfr'><div id='m' onClick={()=>{setRecent(true)}}>최근 매치</div><div>|</div><div id='m'onClick={()=>{setRecent(false)}}>예정 매치</div></div>
            
            {recent ? 
      <div id="matches">
        
        
        {datas.finMatch.map((data) => (
          <div key={data.matchId} id="finMatch" onClick={()=>goMatchDetail(data.matchId)}>
            <div id='matchLeft'>
              <img src={Epl} alt=''></img>
              <div id='date'>
                <div id='time'>{data.date.substring(11,16)}</div>
                <div id='year'>{data.date.substring(0,10)}</div>
                
              </div>
              <div id='place'>{data.stadium}</div>
            </div>
            <div id='matchRight'>
              <div id='team'>
                <div className='team'>
                  <img id='logos' src={data.homeImage} alt=""></img>
                  <div>{data.home}</div>
                </div>
                <div className='team'>
                  <img id='logos' src={data.awayImage} alt=""></img>
                  <div>{data.away}</div>
                </div>
              </div>
              <div id='score'>
                <div className='sc'>{data.homeScore}</div>
                <div id='vs'>VS</div>
                <div className='sc'>{data.awayScore}</div>
              </div>
            </div>
          </div>
        ))}
       
      </div>
     :
      <div id="matches">
        {datas.yetMatch.map((data) => (
          <div key={data.matchId} id="finMatch" onClick={()=>goMatchDetail(data.matchId)}>
          <div id='matchLeft'>
            <img src={Epl} alt=''></img>
            <div id='date'>
              <div id='time'>{data.date.substring(11,16)}</div>
              <div id='year'>{data.date.substring(0,10)}</div>
              
            </div>
            <div id='place'>{data.stadium}</div>
          </div>
          <div id='matchRight'>
            <div id='team'>
              <div className='team'>
                <img id='logos' src={data.homeImage} alt=""></img>
                <div>{data.home}</div>
              </div>
              <div className='team'>
                <img id='logos' src={data.awayImage} alt=""></img>
                <div>{data.away}</div>
              </div>
            </div>
            <div id='score'>
              <div className='sc'>경기 시작 전 입니다.</div>
            </div>
          </div>
        </div>
        ))}
      </div>}
      </div>
    }
            
    </div>
    );
};

export default TeamMatches;