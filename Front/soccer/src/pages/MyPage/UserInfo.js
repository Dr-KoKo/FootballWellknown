import React, { useEffect, useState } from "react";
import axios from "axios";
import "./UserInfo.css";
import Profile from '../../components/assets/epl.png';
import { Link } from "react-router-dom";
import { Outlet, useParams } from "react-router";
import UserDetail from "pages/MyPage/UserDetail"
import UserPredict from "pages/MyPage/UserPredict"
import HorizonLine from "components/HorizonLine"


const MyPage = () => {
    const [datas, setDatas] = useState({ email: "1234", nickname: "1234", point: "0" });
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        axios
            .get(`http://localhost:8080/api/v1/users`, {
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

        <div id="userDiv">
            <div>
                <h1>내 정보</h1>
            </div>
            <HorizonLine />
            <div id="userinfoDiv">
                <img width="200px" src={Profile} />
                <UserDetail />
            </div>
            <div>
                <h1>내 예측</h1>
            </div>
            <HorizonLine />
            <UserPredict />

            <div id="buttons">
                <Link to={"detail"} className="btn">
                    <div id="matchFrame">
                        {/* <img className="btns" id="matchImg" src={Match} alt=''></img> */}
                        <div id="matchText">detail</div>
                    </div>
                </Link>
                <Link to={"predict"} className="btn">
                    <div id="matchFrame">
                        {/* <img className="btns" id="matchImg" src={Player} alt=''></img> */}
                        <div id="matchText">predict</div>
                    </div>
                </Link>
                <Link to={"point"} className="btn">
                    <div id="matchFrame">
                        {/* <img className="btns" id="matchImg" src={Text} alt=''></img> */}
                        <div id="matchText">point</div>
                    </div>
                </Link>
            </div>
            <Outlet></Outlet>
        </div>
    );
};

export default MyPage;
