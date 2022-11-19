import React, { useState, useEffect } from "react";
import { useLocation, useNavigate, useParams } from "react-router";
import { detailBoard, deleteBoard, postComment } from "services/boardServices";
import { getTeam, getMatch } from "services/matchServices";
import { CKEditor } from "@ckeditor/ckeditor5-react";

import ClassicEditor from 'ckeditor5-custom-build/build/ckeditor';
import dateFormat from "dateformat";
import "./BoardDetail.css";
import Loading from "components/Loading";
import LikeButton from "components/board/LikeButton.js";
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
  IconButton,
} from "@mui/material";
import { store } from "../../index";
import { useSelector } from "react-redux";

const BoardDetail = () => {
  // const ClassicEditor = require("../../util/build/ckeditor.js");
  const navigate = useNavigate();
  const { id } = useParams();
  const state = useSelector(state => state);
  const [board, setBoard] = useState(null);
  const [commentlist, setCommentlist] = useState(null);
  const [comment, setComment] = useState(null);
  const [team, setTeam] = useState(null);
  const [match, setMatch] = useState(null);

  const getDetailBoard = async (id) => {
    const result = await detailBoard(id);
    if (result.status == 200) {
      setBoard(result.data.boardDetail);
      setCommentlist(result.data.boardDetail.comments);
      if (result.data.boardDetail.team) {
        createTeam(result.data.boardDetail.team);
      }
      if (result.data.boardDetail.match) {
        createMatch(result.data.boardDetail.match);
      }
    }
  };

  const createTeam = async (teamId) => {
    const result = await getTeam(teamId);

    if (result.status === 200) {
      setTeam(result.data.result);
    }
  };

  const createMatch = async (matchId) => {
    const result = await getMatch(matchId);

    if (result.status === 200) {

      setMatch(result.data.result.matchVO);
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
    const body = JSON.stringify({
      boardId: id,
      comment: comment,
    });
    const result = await postComment(body);
    window.location.reload();
  };

  const postDeleteBoard = async () => {
    const result = await deleteBoard(id);
    if(result.data.status == 200) {
       navigate("../board");
    }
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
                {match != null && (
                  <div>
                    <h2>
                      {match.date} {match.home}vs{match.away}
                    </h2>
                  </div>
                )}
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

              {board.author == store.getState().user.nickname && (
                <Grid item xs="1">
                  <Button
                    variant="contained"
                    onClick={getModifyBoard}
                    sx={{ marginRight: "10px" }}
                  >
                    글 수정
                  </Button>
                </Grid>
              )}
              {(board.author == store.getState().user.nickname ||
                store.getState().auth == "ADMIN") && (
                <Grid item xs="1">
                  <Button
                    color="error"
                    variant="contained"
                    onClick={postDeleteBoard}
                    sx={{ marginRight: "10px" }}
                  >
                    글 삭제
                  </Button>
                </Grid>
              )}
            </Grid>
            <hr></hr>
            <Grid>
              <CKEditor
                editor={ClassicEditor}
                config={{
                  language: "ko",
                  simpleUpload: {
                    uploadUrl: 'localhost:8080/api/vi/editor/upload'
                  },
                }}
                data={board.content}
                onReady={(editor) => {
                  editor.enableReadOnlyMode("my-feature-id");
                  editor.ui.view.toolbar.element.style.display = "none";
                }}
              />
            </Grid>
            <hr></hr>


            <Grid sx={{textAlign:"center"}}>
              <LikeButton boardId={id} />
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
                    {commentlist.map((comment) => (
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
