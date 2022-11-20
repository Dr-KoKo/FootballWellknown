package com.a203.sixback.board;


import com.a203.sixback.board.req.PostBoardLikeReq;
import com.a203.sixback.board.req.PostBoardReq;
import com.a203.sixback.board.req.PostCommentReq;
import com.a203.sixback.board.req.UpdateBoardReq;
import com.a203.sixback.board.res.*;
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
        List<GetBoardRes> boards = boardService.getBoardList(pages);
        long lastPage = boardService.getLastPage();
        return ResponseEntity.status(200).body(BoardRes.of(200, "message", boards, (int) lastPage));
    }

    @GetMapping("/search")
    public ResponseEntity<? extends BaseResponseBody> getBardSearchList(@RequestParam(value = "currentPage") Long page, @RequestParam(value = "type") String type, @RequestParam(value = "keyword") String keyword){
        List<GetBoardRes> boards = boardService.getBoardSearchList(page, type, keyword);
        long lastPage = boardService.getSearchLastPage(page, type, keyword);

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
        GetBoardDetailRes board = boardService.getBoardDetail(boardId);
        List<GetCommentRes> list = boardService.findComments(boardId);
        board.setComments(list);
        return  ResponseEntity.status(200).body(BoardDetailRes.of(200, "GET DETAIL", board));
    }

    @GetMapping("/comments/{boardId}")
    public ResponseEntity getComments(@PathVariable(value ="boardId") long boardId){

        List<GetCommentRes> list = boardService.findComments(boardId);
        return  ResponseEntity.status(200).body(list);
    }

    @PostMapping("")
    public ResponseEntity createBoard(@RequestBody PostBoardReq postBoardReqDTO) {
        return boardService.createBoard(postBoardReqDTO);
    }

    @PostMapping("/update")
    public ResponseEntity updateBoard(@RequestBody UpdateBoardReq updateBoardReqDTO) {


        return boardService.updateBoard(updateBoardReqDTO);
    }

    @PostMapping("/delete/{boardId}")
    public ResponseEntity deleteBoard(@PathVariable(value ="boardId") Long boardId) {
        return boardService.deleteBoard(boardId);
    }

    @PostMapping("/comment")
    public ResponseEntity postComment(@RequestBody PostCommentReq postCommentDTO) {
        return boardService.postComment(postCommentDTO);
    }

    @GetMapping("/likes/{boardId}")
    public ResponseEntity getBoardLike(@PathVariable(value ="boardId") Long boardId) {
        boolean userLiked = boardService.getLike(boardId);
        return  ResponseEntity.status(200).body(BoardLikeRes.of(200, "GET IsLiked", userLiked ));
    }

    @GetMapping("/likes/counts/{boardId}")
    public ResponseEntity getBoardLikeCount(@PathVariable(value ="boardId") Long boardId) {
        int count = boardService.getLikeCount(boardId);
        return  ResponseEntity.status(200).body(BoardLikeCountRes.of(200, "GET LikeCount", count ));
    }

    @PostMapping("/likes")
    public ResponseEntity postBoardLike(@RequestBody PostBoardLikeReq postBoardLikeReq) {
        return boardService.postBoardLike(postBoardLikeReq);
    }

    @PostMapping("/comment/test/mysql")
    public ResponseEntity postCommentMySql(@RequestBody PostCommentReq postCommentReq) {
        return boardService.postCommentMySql(postCommentReq);
    }

    @GetMapping("/comment/test/mysql/{boardId}")
    public ResponseEntity getCommentMySql(@PathVariable(value ="boardId") long boardId){
        List<GetCommentRes> list = boardService.findCommentsMySql(boardId);
        return ResponseEntity.status(200).body(list);
    }

    @PostMapping("/comment/test/mongo")
    public ResponseEntity postCommentMongo(@RequestBody PostCommentReq postCommentReq) {
        return boardService.postCommentMongo(postCommentReq);
    }

    @GetMapping("/comment/test/mongo/{boardId}")
    public ResponseEntity getCommentMongo(@PathVariable(value ="boardId") long boardId){
        List<GetCommentRes> list = boardService.findCommentsMongo(boardId);
        return ResponseEntity.status(200).body(list);
    }
}
