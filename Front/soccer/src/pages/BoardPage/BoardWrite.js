import React, { useState, useEffect } from "react";
import {
  FormControl,
  InputLabel,
  Input,
  Button,
  Select,
  MenuItem,
  Menu,
} from "@mui/material";
import { useNavigate } from "react-router";
import { CKEditor } from "@ckeditor/ckeditor5-react";
import ClassicEditor from "../../util/build/ckeditor";
import { type } from "@testing-library/user-event/dist/type";
import matchers from "@testing-library/jest-dom/matchers";
import { axiosAuth, request } from "services/axios";
const boardWriteUrl = "http://localhost:8080/api/v1/boards/";

const BoardWrite = () => {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [ctgName, setCtgName] = useState("자유");
  const [teams, setTeams] = useState([]);
  const [matches, setMatches] = useState([]);
  const [team, setTeam] = useState(null);
  const [match, setMatch] = useState(null);

  const navigate = useNavigate();

  const createTeamList = async () => {
    const result = await request.get("/api/v1/matches/boards/teams");
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

  const onTitleChanged = (event) => {
    setTitle(event.target.value);
  };

  const createRound = (event) => {
    createMatchList(event);
  };

  const onCtgChaged = (event) => {
    setCtgName(event.target.value);
    console.log();
    if (teams.length === 0 && event.target.value === "팀") {
      createTeamList();
    }
  };

  // 등록 버튼 눌렀을 때
  const onSubmitClicked = async (event) => {
    event.preventDefault();
    console.log(title);
    console.log(content);
    console.log(ctgName);

    let sendTeam = team;
    let sendMatch = match;

    if (ctgName !== "팀") sendTeam = null;
    if (ctgName !== "경기") sendMatch = null;

    const result = await fetch(boardWriteUrl, {
      method: "POST",
      body: JSON.stringify({
        title: title,
        content: content,
        ctgName: ctgName,
        matchId: sendMatch,
        teamId: sendTeam,
      }),
      headers: {
        "Content-Type": "application/json",
        // accessToken: getCookie("accessToken"),
      },
    })
      .then((res) => res.json())
      .then((json) => json);
    if (result.statusCode === 200) {
    }
  };

  const isValid =
    title.trim().length >= 2 &&
    content.trimEnd().length >= 2 &&
    ctgName.trim().length >= 1;

  return (
    <div className="board">
      <h2>게시글 등록</h2>
      <FormControl>
        <InputLabel htmlFor="title">제목</InputLabel>
        <Input id="title" type="text" value={title} onChange={onTitleChanged} />
      </FormControl>

      <Select onChange={onCtgChaged}>
        <MenuItem value="자유">
          {/* <img src="images/a001.PNG" width="30" height="30" /> */}
          자유
        </MenuItem>
        <MenuItem value="공지사항">공지사항</MenuItem>
        <MenuItem value="팀">팀</MenuItem>
        <MenuItem value="경기">경기</MenuItem>
      </Select>

      {/* 팀 선택 시 팀 리스트 */}
      {ctgName === "팀" && (
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
      )}

      {/* 경기 선택 시 라운드 리스트 */}
      {ctgName === "경기" && (
        <Select>
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
      )}

      {/* 라운드 선택시 경기 리스트 */}
      {ctgName === "경기" && (
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
      )}

      <div className="board-editor">
        <div>
          <CKEditor
            editor={ClassicEditor}
            config={{
              language: "ko",
              placeholder: "내용!",
            }}
            onChange={(event, editor) => {
              setContent(editor.getData());
            }}
          />
        </div>
      </div>
      <Button
        className="board-button"
        variant="outlined"
        onClick={onSubmitClicked}
        disabled={!isValid}
      >
        등록하기
      </Button>
    </div>
  );
};

export default BoardWrite;
