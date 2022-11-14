import React from 'react';
import { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router';
import NoImage from '../../components/assets/noimage.jpg';
import "./Padlet.css";
import { Box, Button } from '@mui/material';
const Padlet = () => {
    const SERVER_URL = process.env.REACT_APP_SERVER_URL;
    const navigate = useNavigate();
    const {matchId} = useParams();
    const [board, setBoard]= useState({
        ctgName:"경기", team:null, match:matchId
    });

    const onClickBtn = (matchId) =>{
        navigate("../../board/write", {state : board});
    };
    function strParsing(str){
        var idx = str.search("<img");
        if(idx===-1){
            return NoImage;
        }
        str = str.substring(idx, str.length);
        idx = str.search("https");
        str = str.substring(idx, str.length);
        idx = str.search("\"");
        console.log(idx);
        return str.substring(0,idx);
    }
    function getStr(str){
        var idx = str.search("<p>");
        if(idx===-1){
            return "내용이 없습니다.";
        }
        str=str.substring(idx+3,str.length);
        idx=str.search("<");
        str=str.substring(0,idx);
        if(str.length>20){
            str=str.substring(0,20) +" ...";
        }
        return str;
    }
    
    const [datas, setDatas] = useState([
        {
            id : -1,
            title : "",
            ctgName : "",
            content : "",
            author : "",
            team : 80,
            match : 10,
            createDate : "2022-08-14T01:30:00"
        },
    ]);
    useEffect(() => {
        axios
          .get(`${SERVER_URL}/api/v1/boards/matches/main/${matchId}`)
          .then((response) => {
            setDatas(response.data.boardList.length === 0 ? datas : response.data.boardList);
            console.log(response.data.boardList);
            
          });
      },[] );
    return (
        <div id='mainFrame22'>
            {
            datas[0].id === -1 ?
            <Box>작성된 패들릿이 없습니다</Box>:
            datas.map((data) => (
                <div id='frame22'>                
                    <img className='thuimg' src = {strParsing(data.content)} alt=""></img>
                    <div id='content'>
                        <div id="ttitle">{data.title}</div>
                        <div className='author'>작성자 : {data.author}</div>
                        <div>{getStr(data.content)}</div>
                    </div>
                
                </div>
            ))}
            <Button id='writeBtn' onClick={()=>onClickBtn(datas.id)}>글쓰기</Button>
        </div>    
        
    );
};

export default Padlet;