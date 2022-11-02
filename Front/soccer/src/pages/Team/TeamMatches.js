import axios from 'axios';
import React, { useEffect, useState } from 'react';
import Loading from "components/Loading";
import { useParams } from 'react-router';
import Epl from '../../components/assets/epl.png';
import "./TeamMatches.css";
const TeamMatches = () => {
    const {id} = useParams();
    const [loading, setLoading] = useState(true);
    const [recent, setRecent] = useState(true);
    const [datas, setDatas] = useState({
        teamInfo: "",
        finMatch: [{ date: "" }],
        yetMatch: [],
      });
      useEffect(() => {
        axios
          .get(`http://localhost:8080/api/v1/teams/${id}/details`)
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
          <div key={data.matchId} id="finMatch">
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
          <div key={data.matchId} id="finMatch">
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