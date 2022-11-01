import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router";
import dateFormat, { masks } from "dateformat";
import { CKEditor } from "@ckeditor/ckeditor5-react";
import { FormControl, Button, TextField } from "@mui/material";
import ClassicEditor from "../../util/build/ckeditor";
const boardUrl = "http://localhost:8080/api/v1/boards/";

const BoardDetail = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const [board, setBoard] = useState(null);
  const [comment, setComment] = useState(null);

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

  const onCommentChaged = (event) => {
    setComment(event.target.value);
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
      navigate("/board");
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
          <div className="comment">
            <table>
              <tbody>
                {board.comments.map((comment) => (
                  <tr key={comment.commentId}>
                    <td>{comment.comment}</td>
                    <td>{comment.author}</td>
                    <td>{dateFormat(comment.createDate, "yyyy-mm-dd h:MM")}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          <div className="commentForm">
            <FormControl>
              <TextField
                id="outlined-basic"
                label="댓글 작성창"
                variant="outlined"
                fullWidth
                onChange={onCommentChaged}
              />
              <Button variant="contained" onClick={onSubmitComment}>
                댓글 등록
              </Button>
            </FormControl>
          </div>
        </>
      ) : (
        <h1>로딩중</h1>
      )}
    </>
  );
};

export default BoardDetail;
