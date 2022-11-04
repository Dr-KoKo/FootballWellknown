import React, { useState, useEffect } from "react";
import axios from "axios";
import Loading from "components/Loading";
import { Grid, Card, CardMedia, CardContent, Typography, Avatar, Box } from "@mui/material";
import { deepOrange } from '@mui/material/colors';
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
          <Grid  id='mb-1' item xs={4} p={1}>
            <Card sx={{ maxWidth: 345, height:'100%' }} onClick={() => goPlayerDetail(data.id)}>
              <CardMedia
                component="img"
                sx={{width:'100%', height:'65%', objectFit:'contain'}}
                image={(data.image===null)? {PlayerImg}:data.image}
                alt=""
              />
              <CardContent>
                <Box display={'flex'} flexDirection='column'>
                  <Typography  variant="h6" component="div">
                    {data.name} 
                    <Avatar sx={{ bgcolor: deepOrange[500] }}>
                      {data.number}
                    </Avatar>
                  </Typography>
                </Box>
                <Typography variant="body2" color="text.secondary">
                  <div>{data.country}</div>
                  <div>{data.height}cm {data.weight}kg {2022-(data.birth).substring(0,4)} 세</div>
                  <div>{data.joinMatches}경기 {data.goals}골 {data.assists}어시</div>
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
        </Grid>
        <div id="posFr"><div className="position" id="dfBlock">DeFenders</div></div>
      <Grid container>
        {datas.dfs.map((data) => (
          <Grid id='mb-1' item xs={4} p={1}>
            <Card sx={{ maxWidth: 345, height:'100%' }} onClick={() => goPlayerDetail(data.id)}>
              <CardMedia
                component="img"
                sx={{width:'100%', height:'65%', objectFit:'contain'}}
                image={(data.image===null)? {PlayerImg}:data.image}
                alt=""
              />
              <CardContent>
                <Box display={'flex'} flexDirection='column'>
                  <Typography  variant="h6" component="div">
                    {data.name} 
                    <Avatar sx={{ bgcolor: deepOrange[500] }}>
                      {data.number}
                    </Avatar>
                  </Typography>
                </Box>
                <Typography variant="body2" color="text.secondary">
                  <div>{data.country}</div>
                  <div>{data.height}cm {data.weight}kg {2022-(data.birth).substring(0,4)} 세</div>
                  <div>{data.joinMatches}경기 {data.goals}골 {data.assists}어시</div>
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
        </Grid>
        <div id="posFr"><div className="position" id="mfBlock">MidFielders</div></div>
      <Grid container>
        {datas.mfs.map((data) => (
          <Grid id='mb-1' item xs={4} p={1}>
            <Card sx={{ maxWidth: 345, height :'100%'}} onClick={() => goPlayerDetail(data.id)}>
              <CardMedia
                component="img"
                sx={{width:'100%', height:'65%', objectFit:'contain'}}
                image={(data.image===null)? {PlayerImg}:data.image}
                alt=""
              />
              <CardContent>
                <Box display={'flex'} flexDirection='column'>
                  <Typography  variant="h6" component="div">
                    {data.name} 
                    <Avatar sx={{ bgcolor: deepOrange[500] }}>
                      {data.number}
                    </Avatar>
                  </Typography>
                </Box>
                <Typography variant="body2" color="text.secondary">
                  <div>{data.country}</div>
                  <div>{data.height}cm {data.weight}kg {2022-(data.birth).substring(0,4)} 세</div>
                  <div>{data.joinMatches}경기 {data.goals}골 {data.assists}어시</div>
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
        </Grid>
        <div id="posFr"><div className="position" id="fwBlock">Forwards</div></div>
      <Grid container>
        {datas.fws.map((data) => (
          <Grid id='mb-1' item xs={4} p={1}>
            <Card sx={{ maxWidth: 345, height:'100%'}} onClick={() => goPlayerDetail(data.id)}>
              <CardMedia
                component="img"
                sx={{width:'100%', height:'65%', objectFit:'contain'}}
                height={'100%'}
                image={(data.image===null)? {PlayerImg}:data.image}
                alt=""
              />
              <CardContent>
                <Box display={'flex'} flexDirection='column'>
                  <Typography  variant="h6" component="div">
                    {data.name} 
                    <Avatar sx={{ bgcolor: deepOrange[500] }}>
                      {data.number}
                    </Avatar>
                  </Typography>
                </Box>
                <Typography variant="body2" color="text.secondary">
                  <div>{data.country}</div>
                  <div>{data.height}cm {data.weight}kg {2022-(data.birth).substring(0,4)} 세</div>
                  <div>{data.joinMatches}경기 {data.goals}골 {data.assists}어시</div>
                </Typography>
              </CardContent>
            </Card>
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
