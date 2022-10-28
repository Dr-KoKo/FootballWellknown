import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router';
import axios from 'axios';
import Loading from 'components/Loading';
import Grid from '@mui/material/Grid';
import './PlayerDetail.css';
const PlayerDetail = () => {
    const {id} = useParams();
    const [datas, setDatas] = useState({
        number :"5",
        name : "",
        birth : "",
        position : "",
        history:""
    });
    const [histories, setHistories] =useState([]);
    const [loading, setLoading] = useState(true);
    useEffect(() => {
        axios
          .get(`http://localhost:8080/api/v1/teams/players/${id}`)
          .then((response) => {
            setDatas(response.data.result);
            console.log(response.data.result);
            setLoading(false);
            
          });
      }, []);
      useEffect(()=>{
        setHistories((datas.history).split("\n"));
      },[datas]);
    return (
        <div id='main'>
        <div id='frame'>
            <div id='profile'>
                <img id='image' src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR0rrd_u4sPTBOkZgR5VOmOOTf3KNi5P0vy0g&usqp=CAU" alt=''></img>
                <div id='player'>
                    <div id='pNum'>{datas.number}</div>
                    <div id='pName'><div>{datas.name}
                        </div> <div>({2022-(datas.birth).substring(0,4)})세</div></div>
                    <div id='pptn'>
                        <div>
                        {datas.position}
                        </div>
                        <div>
                        {datas.teamName}
                            </div> </div>
                    <div id='ph'>{datas.height}cm {datas.weight}kg</div>
                    <div id='pb'>{datas.birth} {datas.country}</div>
                    <div id='pb'>{datas.joinMatches}경기 {datas.goals}골 {datas.assists}어시스트</div>
                </div>               
            </div>
            <div>연혁</div>
            <Grid container spacing={2}>
                {histories.map((history) => (
                    <Grid id='hs' item xs={6}>{history}</Grid>
                ))}
            </Grid>
        </div>
        </div>
    );
};

export default PlayerDetail;