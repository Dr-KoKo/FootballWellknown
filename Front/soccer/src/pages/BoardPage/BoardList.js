import React, { useState, useContext, useEffect, useCallback } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Button, Pagination } from "@mui/material";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";

import dayjs from "dayjs";
import utc from "dayjs/plugin/utc";

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
    <div>
      {boards ? (
        <div>
          <div className="board">
            <h1>게시글 목록</h1>
            <table>
              <thead>
                <tr>
                  <th>글번호</th>
                  <th>제목</th>
                  <th>작성자</th>
                </tr>
              </thead>
              <tbody>
                {boards.map((board) => (
                  <tr
                    key={board.id}
                    onClick={() => navigate(`detail/${board.id}`)}
                  >
                    <td>{board.id}</td>
                    <td>{board.title}</td>
                    <td>{board.author}</td>
                  </tr>
                ))}
              </tbody>
            </table>
            <button onClick={() => navigate(`write`)}>글 작성</button>
          </div>
          <Pagination
            count={lastPage}
            page={currentPage}
            showFirstButton
            showLastButton
            onChange={onChangeHandler}
          ></Pagination>
        </div>
      ) : (
        <h1>로딩중</h1>
      )}
    </div>
  );
};

export default BoardList;
