import React, { useEffect, useState } from "react";
// import {UserApi} from "../../api/userApi"
import axios from "axios";
// import GetUserInfo from "api/user";
import Loading from "components/Loading";
// import "./TeamInfo.css";

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

        <div id="teamDiv">

            {loading ? <Loading /> : <div id="teamDet">
                <table id="table">
                    <thead>
                        <tr>
                            <th id="rank">away</th>
                            <th id="result">awayImg</th>
                            <th id="pts">home</th>
                            <th id="pts">homeImg</th>
                            <th id="pts">myPredict</th>
                        </tr>
                    </thead>
                    <tbody>
                        {datas.map((data) => (
                            <tr>
                                <td id="rank">{data.away}</td>
                                <td id="result"><img src={data.awayImg}/></td>
                                <td id="pts">{data.home}</td>
                                <td id="pts"><img src={data.homeImg}/></td>
                                <td id="pts">{data.myPredict}</td>
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
