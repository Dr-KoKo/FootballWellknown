import React, { useState, useEffect } from "react";
import {
  useLocation,
  useNavigate,
  useParams,
  useRouteLoaderData,
} from "react-router";
import dateFormat, { masks } from "dateformat";
import { CKEditor } from "@ckeditor/ckeditor5-react";
import "./BoardDetail.css";
import Loading from "components/Loading";
import {
  FormControl,
  Button,
  TextField,
  Container,
  Grid,
  Box,
  TableContainer,
  TableHead,
  Table,
  TableCell,
  TableBody,
  TableRow,
} from "@mui/material";
import ClassicEditor from "../../util/build/ckeditor";
import { margin } from "@mui/system";
const boardUrl = "http://localhost:8080/api/v1/boards/";

const BoardDetail = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const [board, setBoard] = useState(null);
  const [comment, setComment] = useState(null);
  const [team, setTeam] = useState(null);
  const [match, setMatch] = useState(null);

  const createTeam = async (teamId) => {
    // const result = await request.get("/api/v1/matches/boards/teams");
    const result = await fetch(
      "http://localhost:8080/api/v1/matches/boards/teams/" + teamId,
      {}
    )
      .then((res) => res.json())
      .then((json) => json);
    console.log(result);
    if (result.statusCode === 200) {
      setTeam(result.result);
    }
  };

  const createMatch = async (matchId) => {
    const ress = await fetch(
      "http://localhost:8080/api/v1/matches/match/" + matchId,
      {}
    )
      .then((res) => res.json())
      .then((json) => json);
    if (ress.statusCode === 200) {
      console.log(ress.result.matchVO);
      setMatch(ress.result.matchVO);
    }
  };

  const getDetailBoard = async (id) => {
    const result = await fetch(boardUrl + id, {})
      .then((res) => res.json())
      .then((json) => json);
    if (result.statusCode === 200) {
      // console.log(result);
      console.log(result.boardDetail);
      setBoard(result.boardDetail);
      if (result.boardDetail.team != null) {
        createTeam(result.boardDetail.team);
      }
      if (result.boardDetail.match != null) {
        createMatch(result.boardDetail.match);
      }
    }
  };

  const onCommentChaged = (event) => {
    setComment(event.target.value);
  };

  const getModifyBoard = () => {
    navigate("modify", { state: board });
  };

  const onSubmitComment = async (event) => {
    event.preventDefault();
    console.log(comment);

    const result = await fetch(
      "http://localhost:8080/api/v1/boards/commentMongo",
      {
        method: "POST",
        body: JSON.stringify({ boardId: id, comment: comment }),
        headers: {
          "Content-Type": "application/json",
          // accessToken: getCookie("accessToken"),
        },
      }
    )
      .then((res) => res.json())
      .then((json) => json);

    if (result.statusCode === 200) {
      window.location.reload();
    }
  };

  const deleteBoard = async(event)=> {
    alert("deleteBoard");
  };

  useEffect(() => {
    getDetailBoard(id);
  }, []);

  return (
    <Container>
      <Box
        className="box1"
        sx={{
          borderRadius: "10px",
          margin: "20px 0",
        }}
      >
        {board !== null ? (
          <Grid sx={{ margin: "5px" }}>
            <Grid
              container
              columns={16}
              alignItems={"center"}
              sx={{ margin: "5px" }}
            >
              {team != null && (
                <Grid item xs="1" textAlign={"center"}>
                  <img src={team.logo} height="50" />
                </Grid>
              )}
              <Grid item xs="12">
                <h1>
                  [{board.ctgName}
                  {team != null && " " + team.name}] {board.title}
                </h1>
                {match != null && (<div><h2>{match.date} {match.home}vs{match.away}</h2></div>)}
              </Grid>

              {team == null && <Grid xs="1"></Grid>}

              <Grid textAlign={"right"} item sx={{ fontSize: "20px" }}>
                {dateFormat(board.createDate, "yyyy-mm-dd h:MM")}
              </Grid>
            </Grid>
            <hr></hr>
            <Grid container textAlign={"center"} columns="12">
              <Grid
                item
                xs="10"
                textAlign={"left"}
                sx={{ fontSize: "20px", fontWeight: "bold" }}
              >
                <p>작성자: {board.author}</p>
              </Grid>
              <Grid item xs="1">
                <Button variant="contained" onClick={getModifyBoard} sx={{ marginRight: "10px" }}>
                  글 수정
                </Button>
              </Grid>
              <Grid item xs="1">
                <Button color="error" variant="contained" onClick={deleteBoard} sx={{ marginRight: "10px" }}>
                  글 삭제
                </Button>
              </Grid>
            </Grid>
            <hr></hr>
            <Grid>
              <CKEditor
                editor={ClassicEditor}
                config={{
                  language: "ko",
                }}
                data={board.content}
                onReady={(editor) => {
                  editor.enableReadOnlyMode("my-feature-id");
                  editor.ui.view.toolbar.element.style.display = "none";
                }}
              />
            </Grid>
            <hr></hr>
            <Grid
              className="comment"
              marginTop={"10px"}
              sx={{
                borderRadius: "10px",
                opacity: 1.0,
                backgroundColor: "#C1C1C1",
              }}
            >
              <TableContainer>
                <Table>
                  <TableHead
                    sx={{ borderBotton: "solid", backgroundColor: "gray" }}
                  >
                    <TableCell
                      align="left"
                      sx={{ fontSize: "15px", fontWeight: "bold" }}
                    >
                      작성자
                    </TableCell>
                    <TableCell
                      align="left"
                      sx={{ fontSize: "15px", fontWeight: "bold" }}
                    >
                      내용
                    </TableCell>
                    <TableCell
                      align="right"
                      sx={{ fontSize: "15px", fontWeight: "bold" }}
                    >
                      작성일
                    </TableCell>
                  </TableHead>
                  <TableBody>
                    {board.comments.map((comment) => (
                      <TableRow key={comment.commentId}>
                        <TableCell align="left">{comment.author}</TableCell>
                        <TableCell align="left">{comment.comment}</TableCell>
                        <TableCell align="right">
                          {dateFormat(comment.createDate, "yyyy-mm-dd h:MM")}
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </TableContainer>
            </Grid>
            <hr></hr>
            <Grid container columns={16} sx={{ marginTop: "10px" }}>
              <Grid item xs="14">
                <TextField
                  id="outlined-basic"
                  label="댓글 작성창"
                  variant="outlined"
                  fullWidth
                  onChange={onCommentChaged}
                />
              </Grid>
              <Grid item xs="2" textAlign={"center"}>
                <Button variant="contained" onClick={onSubmitComment}>
                  댓글 등록
                </Button>
              </Grid>
            </Grid>
            <div className="comment"></div>
            <div className="commentForm">
              <FormControl></FormControl>
            </div>
          </Grid>
        ) : (
          <Loading></Loading>
        )}
      </Box>
    </Container>
  );
};

export default BoardDetail;
