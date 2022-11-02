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
            .get(`http://localhost:8080/api/v1/users/points`, {
                headers: {
                    Authorization: `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb25naGFyMjAwNEBnbWFpbC5jb20iLCJyb2xlIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjY4NjQ4MTgzfQ.HOqV8j9U9D3GJMX0eSZtaL-tWffNMCeQNNP6Ei_92WQ`
                }
            })
            .then((response) => {
                console.log(response.data);
                setDatas(response.data.pointList);
                setLoading(false);
            });
    }, []);

    return (

        <div id="teamDiv">

            {loading ? <Loading /> : <div id="teamDet">
                <table id="table">
                    <thead>
                        <tr>
                            <th id="rank">Date</th>
                            <th id="result">Type</th>
                            <th id="pts">Point</th>
                        </tr>
                    </thead>
                    <tbody>
                        {datas.map((data) => (
                            <tr>
                                <td id="rank">{data.getDate}</td>
                                <td id="result">{data.getType}</td>
                                <td id="pts">{data.point}</td>
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
