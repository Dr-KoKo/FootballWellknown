import React, { useState, useContext, useEffect, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import Loading from "components/Loading";
import { getBoardList, getSearchBoardList } from "../../services/boardServices";
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
  TextField,
  Select,
  MenuItem,
  InputLabel,
} from "@mui/material";
import "./BoardList.css";

const BoardList = () => {
  const navigate = useNavigate();
  const [currentPage, setCurrentPage] = useState(1);
  const [lastPage, setLastPage] = useState(10);
  const [boards, setBoards] = useState(false);
  const [type, setType] = useState("all");
  const [keyword, setKeyword] = useState();

  const board = {
    ctgName : "자유",
    teamId : null,
    matchId : null,
  }

  const searchBoardList = async () => {
    const SearchReqDTO = {
      currentPage : currentPage,
      type : type, 
      keyword : keyword,
    }

    const result = await getSearchBoardList(SearchReqDTO);
    console.log(result);
    console.log(result.data);
    if (result.status == 200) {
      setBoards(result.data.boardList);
      setLastPage(result.data.lastPage);
    }
  }

  const onTypeChanged = (event) => {
    setType(event.target.value);
  } 

  const onKeyChanged = (event) => {
    setKeyword(event.target.value);
  }

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
        <Grid container columns={9} textAlign={"center"}>
          <Grid item xs={3}></Grid>
          <Grid item xs={3}textAlign={"center"}>
            <h1>게시글 목록</h1>
          </Grid>
          <Grid item xs = {2}
            textAlign={"right"}
            margin={"20px"}
          >
            <Button variant="contained" onClick={() => navigate(`write`, {state: board})}>
              게시글 작성
            </Button>
          </Grid>
        </Grid>
        
        <hr/>
        <Box className="box-board-search"   
             
        sx={{
          // backgroundColor: "#BFFD9F",
          opacity: 0.8,
          margin: "0",
          p: 1, 
          border: '1px black' 
        }}>
          <Grid
            container
            columns={20}
            sx={{ padding: "5px" }}
            spacing={2}
            alignItems="center"
            textAlign="center"
          > 
            <Grid item xs={1}/>
            <Grid item xs={5}>
              <Select defaultValue={"title"} label="검색" fullWidth onChange={onTypeChanged}>
                <MenuItem value="Title">제목</MenuItem>
                <MenuItem value="Contnet">내용</MenuItem>
                <MenuItem value="All">제목 + 내용</MenuItem>
              </Select>
            </Grid>
            <Grid item xs={10}>
              <TextField fullWidth value={keyword} onChange={onKeyChanged}/>
            </Grid>
            <Grid item xs={3} >
              <Button fullWidth variant="contained" onClick={searchBoardList}>검색</Button>  
            </Grid>
          </Grid>
            <Grid item xs={1}/>
        </Box>

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
          <Loading></Loading>
        )}
      </Box>
    </Container>
  );
};

export default BoardList;
