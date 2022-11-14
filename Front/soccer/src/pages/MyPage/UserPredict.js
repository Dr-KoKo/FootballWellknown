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
import { getUserPredict } from "services/userServices";

const MyPage = () => {
    const [datas, setDatas] = useState([]);
    const [loading, setLoading] = useState(true);

    const getMyPredict = async () => {
        const result = await getUserPredict();

        if(result?.data?.message==="성공"){
            console.log(result.data)
            setDatas(result.data.userPredictList);
            setLoading(false);
        }
    }

    useEffect(() => {
        getMyPredict();
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
                            <th id="rank">내 예측</th>
                            <th id="rank">결과</th>
                        </tr>
                    </thead>
                    <tbody>
                        {datas.map((data) => (
                            <tr id="userPTR2">
                                {/* <td id="rank">{data.away}</td> */}
                                <td id="td1">
                                    <div id="tdContent">
                                        <div id="tdRound">[{data.round}R]</div>
                                        <div id="tdName">{data.home}</div>
                                    <img id="imglogo" src={data.homeImg}/>
                                    <div id="blank">vs</div>
                                    <img id="imglogo" src={data.awayImg}/>
                                    <div id="tdName">{data.away}</div>
                                    
                                    
                                    </div></td>
                                {/* <td id="rank">{data.home}</td> */}
                                <td id="rank">
                                    {data.myPredict === "HOME" ?
                                        data.home + " 승" :
                                        (
                                            data.myPredict === "AWAY" ?
                                            data.away + " 승" :
                                            "무승부"
                                        )
                                    }
                                </td>
                                <td>
                                    {data.matchResult.length == null ?
                                        "시작 전" :
                                        data.myPredict === data.matchResult ?
                                        '적중' :
                                        '미적중'
                                    }
                                </td>
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
