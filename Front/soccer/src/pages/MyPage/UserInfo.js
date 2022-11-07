import React, { useEffect, useState } from "react";
import axios from "axios";
import "./UserInfo.css";
import { Link } from "react-router-dom";
import { Outlet, useParams } from "react-router";

import Box from '@mui/material/Box';
import Switch from '@mui/material/Switch';
import Zoom from '@mui/material/Zoom';
import FormControlLabel from '@mui/material/FormControlLabel';

import UserDetail from "pages/MyPage/UserDetail"
import UserPredict from "pages/MyPage/UserPredict"
import UserBoard from "pages/MyPage/UserBoard"
import UserPoint from "pages/MyPage/UserPoint"
import HorizonLine from "components/HorizonLine"



const MyPage = () => {
    const [datas, setDatas] = useState({ email: "1234", nickname: "1234", point: "0" });
    const [loading, setLoading] = useState(true);

    const [checkedDetail, setCheckedDetail] = useState(false);
    const handleChangeDetail = () => {
        setCheckedDetail((prev) => !prev);
    };
    
    const [checkedPredict, setCheckedPredict] = useState(false);
    const handleChangePredict = () => {
        setCheckedPredict((prev) => !prev);
    };

    const [checkedBoard, setCheckedBoard] = useState(false);
    const handleChangeBoard = () => {
        setCheckedBoard((prev) => !prev);
    };

    const [checkedScore, setCheckedScore] = useState(false);
    const handleChangeScore = () => {
        setCheckedScore((prev) => !prev);
    };

    useEffect(() => {
        axios
            .get(`https://football-wellknown.com/api/v1/users`, {
                headers: {
                    Authorization: `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb25naGFyMjAwNEBnbWFpbC5jb20iLCJyb2xlIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjY4NjQ4MTgzfQ.HOqV8j9U9D3GJMX0eSZtaL-tWffNMCeQNNP6Ei_92WQ`
                }
            })
            .then((response) => {
                console.log(response.data);
                setDatas(response.data);
                setLoading(false);
            });
    }, []);

    // function goTeamDetail(id){
    //   window.location.href=`/teamdetail/${id}`;
    // } 

    return (
        <div id='frf1'>
        <div id="userDiv1">
            <div id="controlDiv">
                <h1>내 정보</h1>
                {/* <FormControlLabel
                    control={<Switch checked={checkedDetail} onChange={handleChangeDetail} />}
                    label="Show"
                /> */}
            </div>
            <HorizonLine />

            
            {/* <div id={checkedDetail+"div"}>
                <Box sx={{ display: 'flex', justifyContent: 'center' }}>
                    <Zoom in={checkedDetail}>{UserDetail()}</Zoom>
                </Box>
            </div> */}
            <Box id="userBox1" sx={{width:"100%" }}>

            <div id="userinfoDiv">
                <UserDetail />
            </div>
            </Box>

            <div id="controlDiv">
                <h1>내 예측</h1>
                <FormControlLabel
                    control={<Switch checked={checkedPredict} onChange={handleChangePredict} />}
                    label="Show"
                />
            </div>
            <HorizonLine />

            <div id={checkedPredict+"div"}>
                <Box sx={{ display: 'flex', width:'100%', justifyContent: 'center' }}>
                    <Zoom in={checkedPredict}>{UserPredict()}</Zoom>
                </Box>
            </div>

            <div id="controlDiv">
            <h1>내 글</h1>
                <FormControlLabel
                    control={<Switch checked={checkedBoard} onChange={handleChangeBoard} />}
                    label="Show"
                />
            </div>
            <HorizonLine />

            <div id={checkedBoard+"div"}>
                <Box sx={{ display: 'flex', justifyContent: 'center' }}>
                    <Zoom in={checkedBoard}>{UserBoard()}</Zoom>
                </Box>
            </div>


            <div id="controlDiv">
            <h1>내 점수</h1>
                <FormControlLabel
                    control={<Switch checked={checkedScore} onChange={handleChangeScore} />}
                    label="Show"
                />
            </div>
            <HorizonLine />

            <div id={checkedScore+"div"}>
                <Box sx={{ display: 'flex', justifyContent: 'center' }}>
                    <Zoom in={checkedScore}>{UserPoint()}</Zoom>
                </Box>
            </div>


        </div>
        </div>
    );
};

export default MyPage;
