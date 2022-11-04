import React, { useEffect, useState } from "react";
// import {UserApi} from "../../api/userApi"
import axios from "axios";
// import GetUserInfo from "api/user";
import Loading from "components/Loading";
import "./UserPredict.css";
import {
    Button,
    Pagination,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Container,
    Box,
    Grid,
    withStyles,
} from "@mui/material";

const MyPage = () => {
    const [datas, setDatas] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        axios
            .get(`http://localhost:8080/api/v1/users/predicts`, {
                headers: {
                    Authorization: `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb25naGFyMjAwNEBnbWFpbC5jb20iLCJyb2xlIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjY4NjQ4MTgzfQ.HOqV8j9U9D3GJMX0eSZtaL-tWffNMCeQNNP6Ei_92WQ`
                }
            })
            .then((response) => {
                console.log(response.data);
                setDatas(response.data.userPredictList);
                setLoading(false);
            });
    }, []);

    // function goTeamDetail(id){
    //   window.location.href=`/teamdetail/${id}`;
    // } 

    return (
        
        <div id="hhhh">

            {loading ? <Loading /> : <div id="hhhh">
                <table id="table">
                    <thead id="radius">
                        <tr id="userPTR">
                            {/* <th id="rank">away</th> */}
                            <th>매치</th>
                            <th id="rank">myPredict</th>
                            <th id="rank">결과</th>
                        </tr>
                    </thead>
                    <tbody>
                        {datas.map((data) => (
                            <tr id="userPTR2">
                                {/* <td id="rank">{data.away}</td> */}
                                <td id="td1">
                                    <div id="tdContent">
                                        <div id="tdRound">[15R]</div>
                                        <div id="tdName">{data.home}</div>
                                    <img id="imglogo" src={data.homeImg}/>
                                    <div id="blank">vs</div>
                                    <img id="imglogo" src={data.awayImg}/>
                                    <div id="tdName">{data.away}</div>
                                    
                                    
                                    </div></td>
                                {/* <td id="rank">{data.home}</td> */}
                                <td id="rank">{data.myPredict}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            }
        </div>
    );
};

export default MyPage;
