import React, { useState, useEffect } from "react";
import {
  FormControl,
  Button,
  Select,
  MenuItem,
  TextField,
  InputLabel,
  Container,
  Box,
  Grid,
} from "@mui/material";

import { useLocation, useNavigate } from "react-router";
import { CKEditor } from "@ckeditor/ckeditor5-react";
// import ClassicEditor from "../../util/build/ckeditor";
import ClassicEditor from 'ckeditor5-custom-build/build/ckeditor';
import { createBoard } from "services/boardServices";
import { getTeamList, getMatchList, getRound } from "services/matchServices";
import "./BoardWrite.css";

const BoardWrite = () => {
  // const ClassicEditor = require("../../util/build/ckeditor.js");
  const { state } = useLocation();
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [ctgName, setCtgName] = useState(state.ctgName);
  const [teams, setTeams] = useState([]);
  const [matches, setMatches] = useState([]);
  const [team, setTeam] = useState(state.team);
  const [round, setRound] = useState(0);
  const [match, setMatch] = useState(state.match);

  
  const navigate = useNavigate();

  const createTeamList = async () => {
    const result = await getTeamList();
    if(result.status === 200) {
      setTeams(result.data.result);
    }
  };

  const createMatchList = async (round) => {
    const result = await getMatchList(round);
    if (result.status === 200) {
      setMatches(result.data.result);
    }
  };

  const onTitleChanged = (event) => {
    setTitle(event.target.value);
  };

  const createRound = (event) => {
    createMatchList(event);
  };

  const onCtgChaged = (event) => {
    setCtgName(event.target.value);
    if (teams.length === 0 && event.target.value === "팀") {
      createTeamList();
    }
  };

  // 등록 버튼 눌렀을 때
  const onSubmitClicked = async (event) => {
    event.preventDefault();

    let sendTeam = team;
    let sendMatch = match;

    if (ctgName !== "팀") sendTeam = null;
    if (ctgName !== "경기") sendMatch = null;

    const body = {
      title: title,
      content: content,
      ctgName: ctgName,
      matchId: sendMatch,
      teamId: sendTeam,
    }

    const result = await createBoard(body);
    if (result.status == 200) {
      navigate(`/board`)
    }
  };

  const getMatch = async () => {
    console.log("AAAA");
    console.log(state.match)
    const result = await getRound(state.match);
    if (result.status === 200) {
      setRound(result.data.result);
      createMatchList(result.data.result);
    }
  };

  const onRoundChanged = async (event) => {
    setRound(event.target.value);
    createMatchList(event.target.value);
  }

  const isValid =
    title.trim().length >= 2 &&
    content.trimEnd().length >= 2 &&
    ctgName.trim().length >= 1;

    useEffect(() => {
      console.log(state.ctgName)
      if ((state.ctgName == "팀")) createTeamList();
      if ((state.ctgName == "경기")) getMatch();
    }, []);
  
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
        <h1 style={{ textAlign: "center" }}>게시글 등록</h1>
        <hr/>
        <Grid display={"flex"} justifyContent={"center"}>
          <TextField
            id="title"
            value={title}
            variant="outlined"
            onChange={onTitleChanged}
            label="제목"
            sx={{ width: "90%", margin: "10px", textAlign: "center" }}
          >
            제목
          </TextField>
        </Grid>

        {/* <Input id='title' type='text' aria-describedfy='title-helper-text' value={title} onChange={onTitleChanged}/> */}
        <Grid
          container
          columns={16}
          sx={{ marginLeft: "5%", marginBottom: "20px" }}
        >
          <Grid item xs={3} sx={{marginRight: "1%"}}>
            <FormControl fullWidth>
              <InputLabel>Select Category</InputLabel>
              <Select value={ctgName} label="카테고리" onChange={onCtgChaged}>
                <MenuItem value="자유">자유</MenuItem>
                <MenuItem value="공지사항">공지사항</MenuItem>
                <MenuItem value="팀">팀</MenuItem>
                <MenuItem value="경기">경기</MenuItem>
              </Select>
            </FormControl>
          </Grid>

          {ctgName === "팀" && (
            <Grid item xs={5}>
              <FormControl fullWidth>
                <InputLabel>Select Team</InputLabel>
                <Select value={team}>
                  {teams.map((team) => (
                    <MenuItem
                      sx = {{display: team.id===0 ? 'none' : false}}
                      disabled = {team.id===0 ? true : false}
                      key={team.id}
                      value={team.id}
                      onClick={() => setTeam(team.id)}
                    >
                      <img src={team.logo} height={23} width={23} />
                      {team.name}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </Grid>
          )}
          {ctgName === "경기" && (
            <Grid item xs={3} sx={{marginRight: "1%"}}>
              <FormControl fullWidth>
                <Select value={round} id="select-round" onChange={onRoundChanged}>
                  {Array(38)
                    .fill()
                    .map((round, i) => (
                      <MenuItem
                        key={i + 1}
                        value={i + 1}
                        onClick={() => createRound(i + 1)}
                      >
                        ROUND {i + 1}
                      </MenuItem>
                    ))}
                </Select>
              </FormControl>
            </Grid>
          )}
          {ctgName === "경기" && (
            <Grid item xs={8}>
              <FormControl fullWidth>
                <Select value={match}>
                  {matches.map((match) => (
                    <MenuItem
                      key={match.matchId}
                      value={match.matchId}
                      onClick={() => setMatch(match.matchId)}
                    >
                      {match.matchName}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </Grid>
          )}
        </Grid>

        <div className="board-editor">
          <div>
            <CKEditor
              editor={ClassicEditor}
              config={{
                language: "ko",
                placeholder: "내용!",                      
                simpleUpload: {
                  uploadUrl: 'https://football-wellknown.com:8080/api/v1/editor/upload'
                },
              }}
              onChange={(event, editor) => {
                setContent(editor.getData());
              }}
            />
          </div>
        </div>
        <Button
          className="board-button"
          variant="contained"
          onClick={onSubmitClicked}
          disabled={!isValid}
          sx={{margin:"10px"}}
        >
          등록하기
        </Button>
      </Box>
    </Container>
  );
};

export default BoardWrite;
