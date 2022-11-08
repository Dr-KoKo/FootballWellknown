import React, { useState, useEffect } from "react";
import { useLocation, useNavigate, useParams } from "react-router";
import { getTeam, getMatch } from "services/matchServices";
import dateFormat from "dateformat";
import { CKEditor } from "@ckeditor/ckeditor5-react";
// import ClassicEditor from "../../util/build/ckeditor";
import ClassicEditor from 'ckeditor5-custom-build/build/ckeditor';
import "./BoardModify.css";
import {
  FormControl,
  Button,
  Container,
  Box,
  Grid,
  InputLabel,
  Select,
  MenuItem,
} from "@mui/material";
import Loading from "components/Loading";

const boardUrl = "http://localhost:8080/api/v1/boards/";

const BoardModify = () => {
  // const ClassicEditor = require("../../util/build/ckeditor.js");
  const navigate = useNavigate();
  const { state } = useLocation();
  const { id } = useParams();
  const [content, setContent] = useState(state.content);
  const [ctgName, setCtgName] = useState(state.ctgName);
  const [team, setTeam] = useState(state.team);
  const [round, setRound] = useState(13);
  const [match, setMatch] = useState(state.match);
  const [teams, setTeams] = useState([]);
  const [matches, setMatches] = useState([]);

  const updateBoard = async (event) => {
    event.preventDefault();

    let sendTeam = team;
    let sendMatch = match;

    if (ctgName !== "팀") sendTeam = null;
    if (ctgName !== "경기") sendMatch = null;

    const body = JSON.stringify({
      boardId: id,
      title: state.title + "(수정)",
      ctgName: ctgName,
      teamId: sendTeam,
      matchId: sendMatch,
      content: content,
    });
    const result = updateBoard(body);
    console.log(result);
    if (result.status == 200) {
    }
  };

  const onCtgChaged = (event) => {
    setCtgName(event.target.value);
    console.log();
    if (teams.length === 0 && event.target.value === "팀") {
      createTeamList();
    }
  };

  const createTeamList = async () => {
    // const result = await request.get("/api/v1/matches/boards/teams");
    const result = await fetch(
      "http://localhost:8080/api/v1/matches/boards/teams",
      {}
    )
      .then((res) => res.json())
      .then((json) => json);
    if (result.statusCode === 200) {
      setTeams(result.result);
    }
  };

  const createMatchList = async (event) => {
    const result = await fetch(
      "http://localhost:8080/api/v1/matches/boards/rounds/" + event,
      {}
    )
      .then((res) => res.json())
      .then((json) => json);
    console.log(result);
    if (result.statusCode === 200) {
      setMatches(result.result);
    }
  };

  const getMatch = async () => {
    const result = await fetch(
      "http://localhost:8080/api/v1/matches/boards/matches/" + state.match,
      {}
    )
      .then((res) => res.json())
      .then((json) => json);
    console.log(result);
    if (result.statusCode === 200) {
      setRound(result.result);
      createMatchList(result.result);
    }
  };

  useEffect(() => {
    if ((state.ctgName = "팀")) createTeamList();
    if ((state.ctgName = "경기")) getMatch();
  }, []);

  return (
    <Container>
      <Box
        className="modify-box"
        sx={{
          borderRadius: "10px",
          margin: "20px 0",
        }}
      >
        {state.title !== null ? (
          <Grid sx={{ margin: "5px" }}>
            <Grid textAlign={"center"}>
              <h2>게시글 수정</h2>
            </Grid>
            <hr />
            <Grid item xs="12">
              <h1>{state.title}</h1>
            </Grid>
            <hr />
            <Grid
              container
              textAlign={"center"}
              columns="12"
              sx={{ margin: "5px" }}
            >
              <Grid
                item
                xs="10"
                textAlign={"left"}
                sx={{ fontSize: "20px", fontWeight: "bold" }}
              >
                <p>작성자: {state.author}</p>
              </Grid>
              <Grid textAlign={"right"} item sx={{ fontSize: "20px" }}>
                {dateFormat(state.createDate, "yyyy-mm-dd h:MM")}
              </Grid>
            </Grid>
            <Grid container columns={16} sx={{ marginBottom: "15px" }}>
              <Grid item xs={3} sx={{ marginRight: "1%" }}>
                <FormControl fullWidth>
                  <InputLabel>Select Category</InputLabel>
                  <Select
                    value={ctgName}
                    label="카테고리"
                    onChange={onCtgChaged}
                  >
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
                <Grid item xs={3} sx={{ marginRight: "1%" }}>
                  <FormControl fullWidth>
                    <Select value={round} id="select-round">
                      {Array(38)
                        .fill()
                        .map((round, i) => (
                          <MenuItem
                            key={i + 1}
                            value={i + 1}
                            onClick={() => createMatchList(i + 1)}
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
            <hr />
            <Grid>
              <CKEditor
                editor={ClassicEditor}
                config={{
                  language: "ko",
                  placeholder: "내용!",
                  simpleUpload: {
                    uploadUrl: 'https://football-wellknown.com:8080/api/vi/editor/upload'
                  },
                }}
                data={content}
                onChange={(event, editor) => {
                  setContent(editor.getData());
                }}
              />
            </Grid>
            <Grid textAlign={"center"}>
              <Button
                size="large"
                variant="contained"
                onClick={updateBoard}
                sx={{ margin: "10px", marginBottom: "20px" }}
              >
                수정하기
              </Button>
            </Grid>
          </Grid>
        ) : (
          <Loading />
        )}
      </Box>
    </Container>
  );
};

export default BoardModify;
