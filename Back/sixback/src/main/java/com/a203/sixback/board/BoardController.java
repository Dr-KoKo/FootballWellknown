package com.a203.sixback.board;


import com.a203.sixback.board.dto.*;
import com.a203.sixback.board.res.BoardDetailRes;
import com.a203.sixback.board.res.BoardRes;
import com.a203.sixback.board.res.CommentRes;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    final BoardService boardService;

    final CommentService commentService;
    @GetMapping("")
    public ResponseEntity<? extends BaseResponseBody> getBoardList(@RequestParam(value = "page", required = false) Integer page) {
        int pages = 1;

        if(page != null) {
            pages = page;
        }
        List<GetBoardResDTO> boards = boardService.getBoardList(pages);
        return ResponseEntity.status(200).body(BoardRes.of(200, "message", boards, boards.size()/10 + 1));
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
//        List<GetCommentResDTO> list = commentService.findComments(boardId);
        List<GetCommentResDTO> list = commentService.findCommentsMongo(boardId);
        board.setComments(list);
        return  ResponseEntity.status(200).body(BoardDetailRes.of(200, "GET DETAIL", board));
    }

    @PostMapping("")
    public ResponseEntity createBoard(@RequestBody PostBoardReqDTO postBoardReqDTO) {
        // 일단 memberId = 1로 테스트
        System.out.println(postBoardReqDTO.getCtgName());
        Long userId = 5L;
        return boardService.createBoard(postBoardReqDTO, userId);
    }

    @PostMapping("/update")
    public ResponseEntity updateBoard(@RequestBody UpdateBoardReqDTO updateBoardReqDTO) {
        Long userId = 5L;
//        return boardService.updateBoard(updateBoardReqDTO, userId);

        return boardService.updateBoard(updateBoardReqDTO, userId);
    }

    @PostMapping("/delete/{boardId}")
    public ResponseEntity deleteBoard(@PathVariable(value ="boardId") Long boardId) {
        Long userId = 5L;
//        return boardService.deleteBoard(boardId, userId);
        return boardService.deleteBoard(boardId, userId);
    }



    @PostMapping("/comment")
    public ResponseEntity postComment(@RequestBody PostCommentDTO postCommentDTO) {
        Long userId = 5L;
        return commentService.postComment(userId, postCommentDTO);
    }

    // 시험용
    @GetMapping("/comment/{boardId}")
    public ResponseEntity getComments(@PathVariable(value = "boardId") Long boardId) {
        List<GetCommentResDTO> list = commentService.findComments(boardId);
        return ResponseEntity.ok(CommentRes.of(200,"GET Comment SUCCESS", list));
    }

    @PostMapping("/commentMongo")
    public ResponseEntity postCommentMongo(@RequestBody PostCommentDTO postCommentDTO) {
        Long userId = 5L;
        return commentService.postCommentMongo(userId, postCommentDTO);
    }

    @GetMapping("/commentMongo/{boardId}")
    public ResponseEntity getCommentsMongo(@PathVariable(value = "boardId") Long boardId) {
        List<GetCommentResDTO> list = commentService.findCommentsMongo(boardId);
        return ResponseEntity.ok(CommentRes.of(200,"GET Comment SUCCESS", list));
    }

    @PostMapping("/comment/update")
    public ResponseEntity updateComment(@RequestBody UpdateCommentDTO updateCommentDTO) {
        Long userId = 5L;
        return commentService.updateComment(userId, updateCommentDTO);
    }

    @PostMapping("/comment/delete")
    public ResponseEntity deleteComment(@RequestBody DeleteCommentDTO deleteCommentDTO) {
        Long userId = 5L;
        return commentService.deleteComment(userId, deleteCommentDTO.getCommentId());
    }

}
