import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router";
import dateFormat, { masks } from "dateformat";
import { CKEditor } from "@ckeditor/ckeditor5-react";
import { FormControl, Button, TextField } from "@mui/material";
import ClassicEditor from "../../util/build/ckeditor";
const boardUrl = "http://localhost:8080/api/v1/boards/";

const BoardModify = (data) => {
  const navigate = useNavigate();
  const { id } = useParams();
  const [board, setBoard] = useState(data);

  const getDetailBoard = async (id) => {
    const result = await fetch(boardUrl + id, {})
      .then((res) => res.json())
      .then((json) => json);
    if (result.statusCode === 200) {
      // console.log(result);
      console.log(result.boardDetail);
      setBoard(result.boardDetail);
    }
  };

  useEffect(() => {
    console.log(11111);
    getDetailBoard(id);
  }, []);

  return (
    <>
      {board !== null ? (
        <>
          <div className="board">
            <h2>게시글 수정</h2>
            <h1>{board.title}</h1>
            <hr className="hrLine"></hr>
            <div className="detail-header">
              <div className="detail-header-left">
                <p>작성자: {board.author}</p>
              </div>
              <div className="detail-header-right">
                <p>작성일: {dateFormat(board.createDate, "yyyy-mm-dd h:MM")}</p>
              </div>
            </div>
            <hr className="hrLine"></hr>
            <div className="board-detail-editor">
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
            </div>
          </div>
        </>
      ) : (
        <h1>로딩중</h1>
      )}
    </>
  );
};

export default BoardModify;
