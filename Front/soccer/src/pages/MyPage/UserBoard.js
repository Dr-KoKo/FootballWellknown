import React, { useEffect, useState } from "react";
// import {UserApi} from "../../api/userApi"
import axios from "axios";
// import GetUserInfo from "api/user";
import Loading from "components/Loading";
import { Link, useNavigate } from "react-router-dom";
import "./UserBoard.css";
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
import { getUserBoard } from "services/userServices";


const MyPage = () => {
    const [boards, setBoards] = useState(false);
    const [loadingBoard, setLoadingBoard] = useState(true);

    const [currentPage, setCurrentPage] = useState(1);
    const [lastPage, setLastPage] = useState(10);


    const onChangeHandler = (event, page) => {
        setCurrentPage(page);
        createBoardList(page);
    };

    const createBoardList = async (currentPage) => {
        const result = await getUserBoard(currentPage);
        if (result.status == 200) {
          // console.log(result.data)
          setBoards(result.data.boardList);
          setLastPage(result.data.lastPage);
          setLoadingBoard(false);
        }
        };

    const navigate = useNavigate();

    useEffect(() => {
        createBoardList(currentPage);

    }, []);

    // function goTeamDetail(id){
    //   window.location.href=`/teamdetail/${id}`;
    // } 

    return (
        <div id="hhhh">
            {loadingBoard ? <Loading /> :

                <div id="teamDet">
                    <Grid
                        display={"flex"}
                        justifyContent={"center"}
                        container
                        columns={16}>
                        <Grid item xs={16}>
                            <TableContainer>
                                <Table>
                                    <TableHead sx={{ borderBotton: "solid", backgroundColor: "white" }}>
                                        <TableRow>
                                            <TableCell align="center" sx={{ fontSize: "20px", fontWeight: "bold" }}>글번호</TableCell>
                                            <TableCell align="center" sx={{ fontSize: "20px", fontWeight: "bold" }} >제목</TableCell>
                                            <TableCell align="center" sx={{ fontSize: "20px", fontWeight: "bold" }}>작성자</TableCell>
                                            <TableCell align="center" sx={{ fontSize: "20px", fontWeight: "bold" }}>카테고리</TableCell>
                                        </TableRow>
                                    </TableHead>
                                    <TableBody>
                                        {boards.map((board) => (
                                            <TableRow
                                                id='ubtr1'                                                
                                                key={board.boardId}
                                                onClick={() => navigate(`/board/${board.boardId}`)}
                                                hover
                                            >
                                                <TableCell align="center">{board.boardId}</TableCell>
                                                <TableCell align="center">{board.title}</TableCell>
                                                <TableCell align="center">{board.writer}</TableCell>
                                                <TableCell align="center">{board.categoryName}</TableCell>
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
