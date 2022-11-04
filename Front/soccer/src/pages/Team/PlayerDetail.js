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
    function position(str){
        if(str==='Goalkeepers') str='GK';
        if(str==='Midfielders') str='MF';
        if(str==='Defenders') str='DF';
        if(str==='Forwards') str='FW';
        return <div id={str}>{str}</div>;
    }
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
            <div id='position'><div id='p'>{position(datas.position)}</div><span id='name'>{datas.name}</span></div>
            <div id='profile'>
                <img id='image' src={datas.image} alt=''></img>
                <div id='player'>
                    <div className='red' id='first'>National : <span id='black'>{datas.country}</span></div>
                    <div className='red'>Number : <span id='black'>{datas.number}</span></div>
                    <div className='red'>Age : <span id='black'>{2022-datas.birth.substring(0,4)}</span></div>
                    <div className='red'>Height : <span id='black'>{datas.height} cm</span></div>
                    <div className='red'>Weight : <span id='black'>{datas.weight} kg</span></div>
                    <div className='red'>Played : <span id='black'>{datas.joinMatches}</span></div>
                    <div className='red'>Goals : <span id='black'>{datas.goals}</span></div>
                    <div className='red'>Assists : <span id='black'>{datas.assists}</span></div>
                    <div className='red hs'>Historys : <span id='black' className='history' ><Grid   container spacing={2}>
                {histories.map((history) => (
                    <Grid id='hs' item xs={6}>{history}</Grid>
                ))}</Grid></span>
           </div>
        </div>
        </div></div></div>
    );
};

export default PlayerDetail;