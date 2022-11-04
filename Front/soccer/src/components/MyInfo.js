import React, { useEffect, useState } from "react";
// import {UserApi} from "../../api/userApi"
import axios from "axios";
import Loading from "components/Loading";
// import "./TeamInfo.css";

const MyInfo = () => {
  const [datas, setDatas] = useState({ email: "1234" , nickname: "1234", point: "0"});
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/v1/users`, {headers: {
        Authorization: `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb25naGFyMjAwNEBnbWFpbC5jb20iLCJyb2xlIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjY4NjQ4MTgzfQ.HOqV8j9U9D3GJMX0eSZtaL-tWffNMCeQNNP6Ei_92WQ`
      }})
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
    
    <div id="teamDiv">
{loading ? <Loading /> : <div id="teamDet">  
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
              <td id="rank">{datas.email}</td>
              <td id="result">{datas.nickname}</td>
              <td id="pts">{datas.point}</td>
            </tr>
          </tbody>
        </table>
    </div>
    }
    </div>
  );
};

export default MyInfo;
