import React, { useEffect, useState } from "react";
// import {UserApi} from "../../api/userApi"
import axios from "axios";
// import GetUserInfo from "api/user";
import Loading from "components/Loading";
// import "./TeamInfo.css";
import Profile from "components/assets/epl.png"
import Box from '@mui/material/Box';
  

const UserDetail = () => {
    const [user, setUser] = useState({ email: "1234", nickname: "1234", point: "0" });
    const [loadingUser, setLoadingUser] = useState(true);

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

    }, []);

    // function goTeamDetail(id){
    //   window.location.href=`/teamdetail/${id}`;
    // } 

    return (
        <Box sx={{ }}>
        <div id="userDiv">
            {loadingUser ? <Loading /> : <div id="userDet">
                <img width="400px" src={Profile} />

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
            </div>
            }
        </div>
        </Box>
    );
};

export default UserDetail;

/*
import * as React from 'react';
import Box from '@mui/material/Box';
import Switch from '@mui/material/Switch';
import Paper from '@mui/material/Paper';
import Zoom from '@mui/material/Zoom';
import FormControlLabel from '@mui/material/FormControlLabel';

const icon = (
  <Paper sx={{ m: 1 }} elevation={4}>
    <Box component="svg" sx={{ width: 100, height: 100 }}>
      <Box
        component="polygon"
        sx={{
          fill: (theme) => theme.palette.common.white,
          stroke: (theme) => theme.palette.divider,
          strokeWidth: 1,
        }}
        points="0,100 50,00, 100,100"
      />
    </Box>
  </Paper>
);

export default function SimpleZoom() {
  const [checked, setChecked] = React.useState(false);

  const handleChange = () => {
    setChecked((prev) => !prev);
  };

  return (
    <Box sx={{ height: 180 }}>
      <FormControlLabel
        control={<Switch checked={checked} onChange={handleChange} />}
        label="Show"
      />
      <Box sx={{ display: 'flex' }}>
        <Zoom in={checked}>{icon}</Zoom>
        <Zoom in={checked} style={{ transitionDelay: checked ? '500ms' : '0ms' }}>
          {icon}
        </Zoom>
      </Box>
    </Box>
  );
}
*/