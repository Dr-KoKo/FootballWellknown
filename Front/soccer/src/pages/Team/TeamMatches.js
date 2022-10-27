import axios from 'axios';
import React, { useEffect, useState } from 'react';
import Loading from "components/Loading";
import { useParams } from 'react-router';
import "./TeamMatches.css";
const TeamMatches = () => {
    const {id} = useParams();
    const [loading, setLoading] = useState(true);
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
      }, []);
    return (        
        <div id="frames">
            {loading ? <Loading /> : <div>
      <div id="matches">
        <div>최근 매치</div>
        {datas.finMatch.map((data) => (
          <div key={data.matchId} id="finMatch">
            <div id="matchInfo">
              <div id="matchDate">{data.date.substring(0, 10)} {data.date.substring(11, 16)}</div>
              <div id="matchStadium">{data.stadium}</div>
            </div>            
            <div id="finMatchData">           
            <div className="team">
              <img src={data.homeImage} alt=""></img>
              <div>{data.home}</div>
            </div>
            <div id="scoreVS">
            <div className="score">{data.homeScore}</div>
            <div className="VS">VS</div>
            <div className="score">{data.awayScore}</div>
            </div>
            <div className="team">
              <img src={data.awayImage} alt=""></img>
              <div>{data.away}</div>
            </div>
          </div>
          </div>
        ))}
      </div>
      <div id="matches">
        <div>예정 매치</div>
        {datas.yetMatch.map((data) => (
          <div key={data.matchId} id="finMatch">
            <div id="matchInfo">
              <div id="matchDate">{data.date.substring(0, 10)} {data.date.substring(11, 16)}</div>
              <div id="matchStadium">{data.stadium}</div>
            </div>   
            <div id="finMatchData">
           
            <div className="team">
              <img src={data.homeImage} alt=""></img>
              <div>{data.home}</div>
            </div>
            <div className="VS2">VS</div>
            <div className="team">
              <img src={data.awayImage} alt=""></img>
              <div>{data.away}</div>
            </div>
          </div>
          </div>
        ))}
      </div>
      </div>
    }
    </div>
    );
};

export default TeamMatches;