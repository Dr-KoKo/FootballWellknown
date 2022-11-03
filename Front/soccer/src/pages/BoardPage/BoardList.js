import React, { useState, useContext, useEffect, useCallback } from "react";
import { Link, useNavigate } from "react-router-dom";
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

import dayjs from "dayjs";
import utc from "dayjs/plugin/utc";
import "./BoardList.css";

const url = "http://localhost:8080/api/v1/boards?page=";

const BoardList = () => {
  const navigate = useNavigate();
  const [currentPage, setCurrentPage] = useState(1);
  const [lastPage, setLastPage] = useState(10);
  const [boards, setBoards] = useState(false);
  const nowTime = dayjs();

  const createBoardList = async (currentPage) => {
    const result = await fetch(url + currentPage, {})
      .then((res) => res.json())
      .then((json) => json);
    if (result.statusCode === 200) {
      console.log(result);
      setBoards(result.boardList);
      setLastPage(result.lastPage);
    }
  };

  useEffect(() => {
    createBoardList(currentPage);
  }, []);

  const onChangeHandler = (event, page) => {
    setCurrentPage(page);
    createBoardList(page);
  };

  console.log(boards);
  return (
    <Container>
      <Box
        sx={{
          backgroundColor: "white",
          borderRadius: "10px",
          opacity: 0.8,
          margin: "20px 0",
        }}
      >
        <Grid textAlign={"center"} sx={{ margin: "10px" }}>
          <h1>게시글 목록</h1>
          <hr />
        </Grid>
        <Grid textAlign={"right"} sx={{ marginRight: "30px", marginBottom: "10px"}}>
          <Button variant="contained" onClick={() => navigate(`write`)}>
            게시글 작성
          </Button>
        </Grid>
        {boards ? (
          <Grid
            display={"flex"}
            justifyContent={"center"}
            container
            columns={16}
          >
            <Grid item xs={16}>
              <TableContainer>
                <Table>
                  <TableHead sx={{ borderBotton: "solid", backgroundColor: "gray"}}>
                    <TableRow>
                      <TableCell align="center" sx={{ fontSize:"20px" ,fontWeight : "bold"}}>글번호</TableCell>
                      <TableCell align="center" sx={{ fontSize:"20px" ,fontWeight : "bold"}} >제목</TableCell>
                      <TableCell align="center" sx={{ fontSize:"20px" ,fontWeight : "bold"}}>작성자</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {boards.map((board) => (
                      <TableRow
                        key={board.id}
                        onClick={() => navigate(`detail/${board.id}`)}
                        hover
                      >
                        <TableCell align="center">{board.id}</TableCell>
                        <TableCell align="center">{board.title}</TableCell>
                        <TableCell align="center">{board.author}</TableCell>
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
                sx={{margin:"15px"}}
              ></Pagination>
            </Grid>
          </Grid>
        ) : (
          <div>loading</div>
        )}
        <div>
          {boards ? (
            <div>
              <div className="board">
                {/* <Button varient="contained" color="veryperi" onClick={()=> navigate(`write`)}>글 작성</Button> */}
                {/* <button onClick={() => navigate(`write`)}>글 작성</button> */}
              </div>
            </div>
          ) : (
            <h1>로딩중</h1>
          )}
        </div>
      </Box>
    </Container>
  );
};

export default BoardList;
