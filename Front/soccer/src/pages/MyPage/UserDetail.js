import React, { useEffect, useState } from "react";
// import {UserApi} from "../../api/userApi"
import axios from "axios";
// import GetUserInfo from "api/user";
import Loading from "components/Loading";
// import "./TeamInfo.css";

const MyPage = () => {
    const [user, setUser] = useState({ email: "1234", nickname: "1234", point: "0" });
    const [boards, setBoards] = useState([{ title: "" }]);
    const [loadingUser, setLoadingUser] = useState(true);
    const [loadingBoard, setLoadingBoard] = useState(true);

    useEffect(() => {
        axios
            .get(`http://localhost:8080/api/v1/users`, {
                headers: {
                    Authorization: `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb25naGFyMjAwNEBnbWFpbC5jb20iLCJyb2xlIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjY4NjQ4MTgzfQ.HOqV8j9U9D3GJMX0eSZtaL-tWffNMCeQNNP6Ei_92WQ`
                }
            })
            .then((response) => {
                console.log(response.data);
                setUser(response.data);
                setLoadingUser(false);
            });
        axios
            .get(`http://localhost:8080/api/v1/users/boards`, {
                headers: {
                    Authorization: `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb25naGFyMjAwNEBnbWFpbC5jb20iLCJyb2xlIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjY4NjQ4MTgzfQ.HOqV8j9U9D3GJMX0eSZtaL-tWffNMCeQNNP6Ei_92WQ`
                }
            })
            .then((response) => {
                console.log(response.data);
                setBoards(response.data.boardList);
                setLoadingBoard(false);
                console.log(boards);
            });

    }, []);

    // function goTeamDetail(id){
    //   window.location.href=`/teamdetail/${id}`;
    // } 

    return (
        <div id="teamDiv">
            {loadingUser || loadingBoard ? <Loading /> : <div id="teamDet">

                <table id="table">
                    <thead>
                        <tr>
                            <th id="rank">Email</th>
                            <th id="result">Nickname</th>
                            <th id="pts">Point</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td id="rank">{user.email}</td>
                            <td id="result">{user.nickname}</td>
                            <td id="pts">{user.point}</td>
                        </tr>
                    </tbody>
                </table>

                <table id="table">
                    <thead>
                        <tr>
                            <th id="rank">title</th>
                            <th id="result">content</th>
                            <th id="pts">createDateTime</th>
                        </tr>
                    </thead>
                    <tbody>
                        {boards.map((data) =>
                            <tr>
                                <td id="rank">{data.title}</td>
                                <td id="result">{data.content}</td>
                                <td id="pts">{data.createDateTime}</td>
                            </tr>)
                        }
                    </tbody>
                </table>
            </div>
            }
        </div>
    );
};

export default MyPage;
