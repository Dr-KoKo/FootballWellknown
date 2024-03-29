import React, { useEffect, useState } from "react";
// import {UserApi} from "../../api/userApi"
import axios from "axios";
// import GetUserInfo from "api/user";
import Loading from "components/Loading";
import "./UserDetail.css";
import Profile from "components/assets/epl.png"
import Gold from "components/assets/gold.png"
import Silver from "components/assets/silver.png"
import Bronze from "components/assets/bronze.png"
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TextField,
  Container,
  Box,
  Grid,
  withStyles,
} from "@mui/material";
import { Margin } from "@mui/icons-material";
import { getUserInfo, getUserRank, updateNickname } from "services/userServices";


const UserDetail = () => {
  const [user, setUser] = useState({ email: "1234", nickname: "1234", point: "0" });
  const [rank, setRank] = useState({ rank: "1", rankDaily: "1", rankWeekly: "1" });
  const [loadingUser, setLoadingUser] = useState(true);
  const [loadingRank, setLoadingRank] = useState(true);
  const [open, setOpen] = useState(false);
  const [newNickname, setNewNickname] = useState('');

  const getMyInfo = async () => {
    const result = await getUserInfo();

    if (result?.data?.message === "성공") {
      console.log(result.data)
      setUser(result.data);
      setLoadingUser(false);
    }
  }

  const getMyRank = async () => {
    const result = await getUserRank();

    if (result?.data?.message === "성공") {
      console.log(result.data)
      setRank(result.data)
      setLoadingRank(false);
    }
  }

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleChange = (event) => {
    setNewNickname(event.target.value);
  }

  const updateNick = async () => {
    user.nickname = newNickname;
    await updateNickname(user)
    .then(()=>{
      alert('변경되었습니다');
      handleClose();
    })
    .catch((err)=>{
      alert('변경에 실패했습니다');
    })
  }

  useEffect(() => {
    getMyInfo()
    getMyRank()
  }, []);

  // function goTeamDetail(id){
  //   window.location.href=`/teamdetail/${id}`;
  // } 

  return (
    <div id="userDiv">
      {loadingUser || loadingRank ? <Loading /> : <div id="userDet1">
        <div id="ff">
          {rank.rank < 10 ?
            <img id="medal" width="300px" height="300px" src={Gold} />
            :
            rank.rank < 20 ?
              <img id="medal" width="300px" height="300px" src={Silver} />
              :
              <img id="medal" width="300px" height="300px" src={Bronze} />
          }
          <div id="rankFont">
          {rank.rank}위/{rank.userNum}명
          </div>
        </div>
        <Grid item xs={16} sx={{ width: '60%' }} >
          <TableContainer >
            <Table>
              <TableBody>
                <TableRow
                  // key={user.email}
                  hover
                >
                  <TableCell align="center">Email</TableCell>
                  <TableCell align="center">{user.email}</TableCell>
                </TableRow>
                <TableRow
                  // key={user.email}
                  hover
                >
                  <TableCell align="center">Nickname</TableCell>
                  <TableCell align="center">{user.nickname}</TableCell>
                  <TableCell align="center">
                    <Button onClick={()=>handleClickOpen()}>변경</Button>
                  </TableCell>
                  <Dialog open={open} onClose={handleClose}>
                    <DialogTitle>Subscribe</DialogTitle>
                    <DialogContent>
                      <DialogContentText>
                        변경할 닉네임을 입력하세요
                      </DialogContentText>
                      <TextField
                        autoFocus
                        margin="dense"
                        id="name"
                        label="새로운 닉네임"
                        type="email"
                        fullWidth
                        variant="standard"
                        onChange={handleChange}
                      />
                    </DialogContent>
                    <DialogActions>
                      <Button onClick={()=>updateNick()}>확인</Button>
                      <Button onClick={handleClose}>취소</Button>
                    </DialogActions>
                  </Dialog>
                </TableRow>
                <TableRow
                  // key={user.email}
                  hover
                >
                  <TableCell align="center">Point</TableCell>
                  <TableCell align="center">{user.point}점</TableCell>
                </TableRow>
                <TableRow
                  // key={user.email}
                  hover
                >
                  <TableCell align="center">일간 랭킹</TableCell>
                  <TableCell align="center">{rank.rankDaily}위/{rank.userNum}명</TableCell>
                </TableRow>
                <TableRow
                  // key={user.email}
                  hover
                >
                  <TableCell align="center">주간 랭킹</TableCell>
                  <TableCell align="center">{rank.rankWeekly}위/{rank.userNum}명</TableCell>
                </TableRow>
              </TableBody>
            </Table>
          </TableContainer>
        </Grid>
      </div>
      }
    </div>
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