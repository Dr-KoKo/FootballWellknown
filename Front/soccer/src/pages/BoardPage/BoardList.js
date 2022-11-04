import React, { useState, useContext, useEffect, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import Loading from "components/Loading";
import { getBoardList } from "../../services/boardServices";
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
import "./BoardList.css";

const BoardList = () => {
  const navigate = useNavigate();
  const [currentPage, setCurrentPage] = useState(1);
  const [lastPage, setLastPage] = useState(10);
  const [boards, setBoards] = useState(false);

  const createBoardList = async (currentPage) => {
    const result = await getBoardList(currentPage);
    if (result.status == 200) {
      // console.log(result.data)
      setBoards(result.data.boardList);
      setLastPage(result.data.lastPage);
    }
  };

  useEffect(() => {
    createBoardList(currentPage);
  }, []);

  const onChangeHandler = (event, page) => {
    setCurrentPage(page);
    createBoardList(page);
  };

  return (
    <Container>
      <Box
        className="box-board-list"
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
        <Grid
          textAlign={"right"}
          sx={{ marginRight: "30px", marginBottom: "10px" }}
        >
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
                  <TableHead
                    sx={{ borderBotton: "solid", backgroundColor: "gray" }}
                  >
                    <TableRow>
                      <TableCell
                        align="center"
                        sx={{ fontSize: "20px", fontWeight: "bold" }}
                      >
                        글번호
                      </TableCell>
                      <TableCell
                        align="center"
                        sx={{ fontSize: "20px", fontWeight: "bold" }}
                      >
                        제목
                      </TableCell>
                      <TableCell
                        align="center"
                        sx={{ fontSize: "20px", fontWeight: "bold" }}
                      >
                        작성자
                      </TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {boards.map((board) => (
                      <TableRow
                        key={board.id}
                        onClick={() => navigate(`${board.id}`)}
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
                sx={{ margin: "15px" }}
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
            <Loading></Loading>
          )}
        </div>
      </Box>
    </Container>
  );
};

export default BoardList;
