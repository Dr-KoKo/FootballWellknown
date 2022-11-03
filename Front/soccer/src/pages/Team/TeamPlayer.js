import React, { useState, useEffect } from "react";
import axios from "axios";
import Loading from "components/Loading";
import { Grid } from "@mui/material";
import { useParams } from "react-router";
import PlayerImg from "../../components/assets/playerf.jpg";
import "./TeamPlayer.css"
const TeamPlayer = () => {
  const { id } = useParams();
  const [datas, setDatas] = useState({
    gks: [], fws: [], dfs: [], mfs: []
  });
  function goPlayerDetail(player) {
    window.location.href = `/player/${player}`;
  }
  const [loading, setLoading] = useState(true);
  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/v1/teams/${id}/players`)
      .then((response) => {
        setDatas(response.data.result);
        setLoading(false);
      });
  }, []);
  return (
    <div id="frame">
    {loading ? <Loading /> : 
    <div>
      <div id="coach">
        <div id="posFr"><div className="position" id="coachBlock">COACH</div></div>
        <div id="coachProfile">
          <img id="coachImg" src={datas.coachImage} alt=""></img>
          <div id="coachEx">
            <div>{datas.coachName} ({datas.coachAge})</div>
            <div id="coachContent">
              <div>{datas.country}</div>             
            </div>
          </div>
        </div>
      </div>
      <div id="posFr"><div className="position" id="gkBlock">GoalKeepers</div></div>
      <Grid container>
        {datas.gks.map((data) => (
          <Grid  id='mb-1' item xs={4}>
            <div onClick={() => goPlayerDetail(data.id)}>
            <div id="numberImage">
            <img id="playerProfile" src={(data.image===null)? {PlayerImg}:data.image} alt=""></img>
            <div id="number">{data.number}</div>
            </div>
            
            <div>{data.name} {data.country}</div>
            <div>{data.height}cm {data.weight}kg {2022-(data.birth).substring(0,4)} 세</div>
            <div>{data.joinMatches}경기 {data.goals}골 {data.assists}어시</div>
            </div>
          </Grid>
        ))}
        </Grid>
        <div id="posFr"><div className="position" id="dfBlock">DeFenders</div></div>
      <Grid container>
        {datas.dfs.map((data) => (
          <Grid id='mb-1' item xs={4}>
            <div onClick={() => goPlayerDetail(data.id)}>
            <div id="numberImage">
            <img id="playerProfile" src={(data.image==="")? PlayerImg:data.image} alt=""></img>
            <div id="number">{data.number}</div>
            </div>
            
            <div>{data.name} {data.country}</div>
            <div>{data.height}cm {data.weight}kg {2022-(data.birth).substring(0,4)} 세</div>
            <div>{data.joinMatches}경기 {data.goals}골 {data.assists}어시</div>
            </div>
          </Grid>
        ))}
        </Grid>
        <div id="posFr"><div className="position" id="mfBlock">MidFielders</div></div>
      <Grid container>
        {datas.mfs.map((data) => (
          <Grid id='mb-1' item xs={4}>
            <div onClick={() => goPlayerDetail(data.id)}>
            <div id="numberImage">
            <img id="playerProfile" src={(data.image===null)? {PlayerImg}:data.image} alt=""></img>
            <div id="number">{data.number}</div>
            </div>
            
            <div>{data.name} {data.country}</div>
            <div>{data.height}cm {data.weight}kg {2022-(data.birth).substring(0,4)} 세</div>
            <div>{data.joinMatches}경기 {data.goals}골 {data.assists}어시</div>
            </div>
          </Grid>
        ))}
        </Grid>
        <div id="posFr"><div className="position" id="fwBlock">Forwards</div></div>
      <Grid container>
        {datas.fws.map((data) => (
          <Grid id='mb-1' item xs={4}>
            <div onClick={() => goPlayerDetail(data.id)}>
            <div id="numberImage">
            <img id="playerProfile" src={(data.image===null)? {PlayerImg}:data.image} alt=""></img>
            <div id="number">{data.number}</div>
            </div>
            
            <div>{data.name} {data.country}</div>
            <div>{data.height}cm {data.weight}kg {2022-(data.birth).substring(0,4)} 세</div>
            <div>{data.joinMatches}경기 {data.goals}골 {data.assists}어시</div>
            </div>
          </Grid>
        ))}
        </Grid>
      <div></div>
    </div>
  }
  </div>
  );
};

export default TeamPlayer;
