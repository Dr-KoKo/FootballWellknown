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
import { getUserPoint } from "services/userServices";

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
        const result = await getUserPoint(currentPage);
        if(result.status == 200){
            setDatas(result.data.pointList)
            setLastPage(result.data.lastPage);
            setLoading(false);
        }
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
