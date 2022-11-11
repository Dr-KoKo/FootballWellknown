import axios from "axios";
import { request, axiosAuth } from "./axios.js";

const BOARD_URL = "api/v1/boards";
const BOARD_LIKE_URL = "api/v1/boards/likes/"
const COMMENT_URL = "api/v1/boards/comment";


export const getBoardList = async (currentPage) => {
  try {
    const payload = await request.get(`${BOARD_URL}?page=${currentPage}`);
    return payload;
  } catch (err) {
    return err;
  }
};


export const getSearchBoardList = async (dataToSubmit) => {
  try {
    const payload = await request.get(`${BOARD_URL}/search?currentPage=${dataToSubmit.currentPage}&type=${dataToSubmit.type}&keyword=${dataToSubmit.keyword}`)
    console.log(payload);
    return payload;
  } catch(err){
    return err;
  }
}

// board 게시글 작성
export const createBoard = async (dataToSubmit) => {
  try {
    const payload = await axiosAuth.post(BOARD_URL, dataToSubmit);
    return payload;
  } catch (err) {
    return err;
  }
};

export const deleteBoard = async (boardId) => {
  try {
    const payload = await axiosAuth.post(`${BOARD_URL}/delete/${boardId}`);
    return payload;
  } catch (err) {
    return err;
  }
};

export const detailBoard = async (id) => {
  try {
    const payload = await request.get(`${BOARD_URL}/${id}`);
    console.log(payload);
    return payload;
  } catch (err) {
    return err;
  }
};

export const postComment = async (dataToSubmit) => {
  try {
    const payload = await axiosAuth.post(COMMENT_URL, dataToSubmit);
    return payload;
  } catch (err) {
    return err;
  }
};

export const updateBoard = async (dataToSubmit) => {
  try {
    const payload = null;
    return payload;
  } catch (err) {
    return err;
  }
};

export const checkBoardLike = async(boardId) => {
  try {
    // const result = await fetch(
    // `http://localhost:8080/api/v1/boards/likes/${boardId}`,
    //   {}
    // )
    // .then((res) => res.json())
    // .then((json) => json)
    // console.log(result);
    // return result;

    const payload = await axiosAuth.get(`${BOARD_LIKE_URL}/${boardId}`)
    console.log(payload);
    return payload;
  } catch (err) {
    return err;
  }
};

export const getHowLiked = async(boardId) => {
  try {
    const payload = await request.get(`${BOARD_LIKE_URL}/counts/${boardId}`)
    return payload;
  } catch(err) {
    return err;
  }
}

export const postBoardLike = async(dataToSubmit) => {
 try {
  // const payload = await fetch(`http://localhost:8080/api/v1/boards/likes`, {
  //               method: 'POST',
  //               Headers: {
  //                   'Content-Type': 'application/json'
  //               },
  //               body: dataToSubmit
  //           }
  //       )            
  //           .then((res) => res.json())
  //           .then((json) => json)
  const payload = await axiosAuth.post(`${BOARD_LIKE_URL}`, dataToSubmit)
  console.log(payload);
  return payload;
 } catch(err) {
  return err;
 }
}