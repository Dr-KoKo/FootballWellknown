package com.a203.sixback.board;


import com.a203.sixback.board.dto.*;
import com.a203.sixback.board.res.BoardDetailRes;
import com.a203.sixback.board.res.BoardRes;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    final BoardService boardService;

    @GetMapping("")
    public ResponseEntity<? extends BaseResponseBody> getBoardList(@RequestParam(value = "page", required = false) Integer page) {
        int pages = 1;

        if(page != null) {
            pages = page;
        }
        List<GetBoardResDTO> boards = boardService.getBoardList(pages);
        long lastPage = boardService.getLastPage();
        return ResponseEntity.status(200).body(BoardRes.of(200, "message", boards, (int) lastPage));
    }

    @GetMapping("/search")
    public ResponseEntity<? extends BaseResponseBody> getBardSearchList(@RequestBody SearchReqDTO searchDTO){
        List<GetBoardResDTO> boards = boardService.getBoardSearchList(searchDTO);
        long lastPage = boardService.getSearchLastPage(searchDTO);
        return ResponseEntity.status(200).body(BoardRes.of(200, "searchSuccess", boards, (int) lastPage));
    }

    @GetMapping("/matches/{matchId}")
    public ResponseEntity matchBoard(@RequestParam(value="page", required = true) Integer page, @PathVariable(value = "matchId") Long matchId) {
        int pages = 1;
        if(page != null) {
            pages = page;
        }
        return boardService.getMatchBoard(matchId, pages);
    }

    @GetMapping("/matches/main/{matchId}")
    public ResponseEntity matchTop4Board(@PathVariable(value = "matchId") Long matchId) {
        return boardService.getMatchTop4Board(matchId);
    }


    @GetMapping("/teams/{teamId}")
    public ResponseEntity teamBoard(@RequestParam(value="page", required = true) Integer page, @PathVariable(value = "teamId") Long teamId) {
        int pages = 1;

        if (page != null) {
            pages = page;
        }
        return boardService.getTeamBoard(teamId, pages);
    }

    @GetMapping("/teams/main/{teamId}")
    public ResponseEntity teamTop4Board(@PathVariable(value = "teamId") int teamId) {
        return boardService.getTeamTop4Board(teamId);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity getBoardDetail(@PathVariable(value ="boardId") long boardId){
        GetBoardDetailResDTO board = boardService.getBoardDetail(boardId);
        List<GetCommentResDTO> list = boardService.findComments(boardId);
        board.setComments(list);
        return  ResponseEntity.status(200).body(BoardDetailRes.of(200, "GET DETAIL", board));
    }

    @GetMapping("/comments/{boardId}")
    public ResponseEntity getComments(@PathVariable(value ="boardId") long boardId){

        List<GetCommentResDTO> list = boardService.findComments(boardId);
        return  ResponseEntity.status(200).body(list);
    }

    @PostMapping("")
    public ResponseEntity createBoard(@RequestBody PostBoardReqDTO postBoardReqDTO) {
        return boardService.createBoard(postBoardReqDTO);
    }

    @PostMapping("/update")
    public ResponseEntity updateBoard(@RequestBody UpdateBoardReqDTO updateBoardReqDTO) {


        return boardService.updateBoard(updateBoardReqDTO);
    }

    @PostMapping("/delete/{boardId}")
    public ResponseEntity deleteBoard(@PathVariable(value ="boardId") Long boardId) {
        return boardService.deleteBoard(boardId);
    }



    @PostMapping("/comment")
    public ResponseEntity postComment(@RequestBody PostCommentDTO postCommentDTO) {
        return boardService.postComment(postCommentDTO);
//        return commentService.postComment(postCommentDTO);
    }

//    @PostMapping("/commentMongo")
//    public ResponseEntity postCommentMongo(@RequestBody PostCommentDTO postCommentDTO) {
//        return commentService.postCommentMongo(postCommentDTO);
//    }

//    @GetMapping("/commentMongo/{boardId}")
//    public ResponseEntity getCommentsMongo(@PathVariable(value = "boardId") Long boardId) {
//        List<GetCommentResDTO> list = commentService.findCommentsMongo(boardId);
//        return ResponseEntity.ok(CommentRes.of(200,"GET Comment SUCCESS", list));
//    }

//    @PostMapping("/comment/update")
//    public ResponseEntity updateComment(@RequestBody UpdateCommentDTO updateCommentDTO) {
//
//        return commentService.updateComment(updateCommentDTO);
//    }

//    @PostMapping("/comment/delete")
//    public ResponseEntity deleteComment(@RequestBody DeleteCommentDTO deleteCommentDTO) {
//        return commentService.deleteComment(deleteCommentDTO.getCommentId());
//    }

}
