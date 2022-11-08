import React, { useState } from "react";
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
import { useNavigate } from "react-router";
import { CKEditor } from "@ckeditor/ckeditor5-react";
// import ClassicEditor from "../../util/build/ckeditor";
import ClassicEditor from 'ckeditor5-custom-build/build/ckeditor';
import { createBoard } from "services/boardServices";
import { getTeamList } from "services/matchServices";
import "./BoardWrite.css";

const BoardWrite = () => {
  // const ClassicEditor = require("../../util/build/ckeditor.js");
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [ctgName, setCtgName] = useState("자유");
  const [teams, setTeams] = useState([]);
  const [matches, setMatches] = useState([]);
  const [team, setTeam] = useState(null);
  const [match, setMatch] = useState(null);

  const navigate = useNavigate();

  const createTeamList = async () => {
    const result = await getTeamList();
    if(result.status === 200) {
      setTeams(result.data.result);
    }
  };

  const createMatchList = async (round) => {
    const result = await fetch(
      "http://localhost:8080/api/v1/matches/boards/rounds/" + round,
      {}
    )
      .then((res) => res.json())
      .then((json) => json);
    console.log(result);
    if (result.statusCode === 200) {
      setMatches(result.result);
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

  const isValid =
    title.trim().length >= 2 &&
    content.trimEnd().length >= 2 &&
    ctgName.trim().length >= 1;

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
              <Select label="카테고리" onChange={onCtgChaged}>
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
                <Select>
                  {teams.map((team) => (
                    <MenuItem
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
                <Select id="select-round">
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
                <Select>
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
