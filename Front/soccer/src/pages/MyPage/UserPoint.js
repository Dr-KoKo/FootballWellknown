import React, { useEffect, useState } from "react";
// import {UserApi} from "../../api/userApi"
import axios from "axios";
// import GetUserInfo from "api/user";
import Loading from "components/Loading";
// import "./TeamInfo.css";
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

    const [currentPage, setCurrentPage] = useState(1);
    const [lastPage, setLastPage] = useState(10);

    const onChangeHandler = (event, page) => {
        setCurrentPage(page);
        createPointList(page);
    };

    const createPointList = async (currentPage) => {
        axios
            .get(`https://football-wellknown.com/api/v1/users/points/${currentPage}`, {
                headers: {
                    Authorization: `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb25naGFyMjAwNEBnbWFpbC5jb20iLCJyb2xlIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjY4NjQ4MTgzfQ.HOqV8j9U9D3GJMX0eSZtaL-tWffNMCeQNNP6Ei_92WQ`
                }
            })
            .then((response) => {
                console.log(response.data);
                setDatas(response.data.pointList);
                // response.json()
                setLastPage(response.data.lastPage);
                setLoading(false);
            });
    };


    useEffect(() => {
        createPointList(currentPage);
    }, []);

    return (
        <div id="hhhh">
            {loading ? <Loading /> :

                <div id="teamDet">
                    <Grid
                        display={"flex"}
                        justifyContent={"center"}
                        container
                        columns={16}
                    >
                        <Grid item xs={16}>
                            <TableContainer>
                                <Table>
                                    <TableHead sx={{ borderBotton: "solid", backgroundColor: "white" }}>
                                        <TableRow>
                                            <TableCell align="center" sx={{ fontSize: "20px", fontWeight: "bold" }}>날짜</TableCell>
                                            <TableCell align="center" sx={{ fontSize: "20px", fontWeight: "bold" }} >종류</TableCell>
                                            <TableCell align="center" sx={{ fontSize: "20px", fontWeight: "bold" }}>포인트</TableCell>
                                        </TableRow>
                                    </TableHead>
                                    <TableBody>
                                        {datas.map((point) => (
                                            <TableRow
                                                id='ptr1'
                                                key={point.id}
                                            >
                                                <TableCell align="center">{point.getDate}</TableCell>
                                                <TableCell align="center">{point.getType}</TableCell>
                                                <TableCell align="center">{point.point}</TableCell>
                                            </TableRow>
                                        ))}
                                    </TableBody>
                                </Table>
                            </TableContainer>
                        </Grid>
                        <Grid item>
                            <Pagination
                                count={lastPage}
                                page={currentPage}
                                showFirstButton
                                showLastButton
                                onChange={onChangeHandler}
                                sx={{ margin: "15px" }}
                            ></Pagination>
                        </Grid>
                    </Grid>
                </div>
            }
        </div>
    );
};

export default MyPage;
