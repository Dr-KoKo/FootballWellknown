package com.a203.sixback.board;

import com.a203.sixback.auth.UserPrincipal;
import com.a203.sixback.board.dto.GetCommentResDTO;
import com.a203.sixback.board.dto.PostCommentDTO;
import com.a203.sixback.board.dto.UpdateCommentDTO;
import com.a203.sixback.db.entity.Board;
import com.a203.sixback.db.entity.Comment;
import com.a203.sixback.db.entity.CommentMongo;
import com.a203.sixback.db.entity.User;
import com.a203.sixback.db.mongo.CommentRepoMongoDB;
import com.a203.sixback.db.repo.BoardRepo;
import com.a203.sixback.db.repo.UserRepo;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserRepo userRepo;
    private final BoardRepo boardRepo;

    private final CommentRepoMongoDB commentRepoMongo;

//    public ResponseEntity postComment(PostCommentDTO postCommentDTO) {
//        User user = null;
//        try {
//            user = user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
//        } catch (Exception e) {
//            return ResponseEntity.status(400).body(BaseResponseBody.of(400, "No User"));
//        }
//        Board board = null;
//        try {
//            board = boardRepo.findById(postCommentDTO.getBoardId()).get();
//        } catch(Exception e) {
//            return ResponseEntity.status(400).body(BaseResponseBody.of(400,  "No Board"));
//        }
//
//        Comment comment = Comment.builder()
//                .comment(postCommentDTO.getComment())
//                .user(user)
//                .board(board)
//                .build();
//        commentRepo.save(comment);
//        return ResponseEntity.ok(BaseResponseBody.of(200, "Post Comment Success"));
//    }


//    public List<GetCommentResDTO> findComments(Long boardId) {
//        List<Comment> comments = commentRepo.findAllByBoardId(boardId);
//        List<GetCommentResDTO> getComments = new LinkedList<>();
//
//        for(Comment comment : comments){
//            getComments.add(new GetCommentResDTO().builder()
//                    .author(comment.getUser().getNickname())
//                    .comment(comment.getComment())
//                    .build()
//            );
//        }
//        return getComments;
//    }

    public ResponseEntity postCommentMongo(PostCommentDTO postCommentDTO) {
        User user = null;
        try {
            user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(BaseResponseBody.of(400, "No User"));
        }
        Board board = null;
        try {
            board = boardRepo.findById(postCommentDTO.getBoardId()).get();
        } catch(Exception e) {
            return ResponseEntity.status(400).body(BaseResponseBody.of(400,  "No Board"));
        }

        CommentMongo commentMongo = CommentMongo.builder()
                .comment(postCommentDTO.getComment())
                .author(user.getNickname())
                .authorId(user.getId())
                .boardId(board.getId())
                .createDate(LocalDateTime.now())
                .build();
        commentRepoMongo.save(commentMongo);
        return ResponseEntity.ok(BaseResponseBody.of(200, "Post Comment Success"));
    }

    public List<GetCommentResDTO> findCommentsMongo(Long boardId) {
        List<CommentMongo> comments = commentRepoMongo.findAllByBoardId(boardId);
        List<GetCommentResDTO> getComments = new LinkedList<>();

        for(CommentMongo comment : comments){
            getComments.add(new GetCommentResDTO().builder()
                    .commentId(1)
                    .author(comment.getAuthor())
                    .comment(comment.getComment())
                    .createDate(comment.getCreateDate())
                    .build()
            );
        }
        return getComments;
    }

    public ResponseEntity updateComment(UpdateCommentDTO updateCommentDTO) {
        User user = null;
        try {
            user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(BaseResponseBody.of(400, "No User"));
        }
        CommentMongo comment = null;
        try {
            comment = commentRepoMongo.findById(updateCommentDTO.getCommentId()).get();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(BaseResponseBody.of(400, "No Comment"));
        }

        if(!comment.getAuthor().equals(user.getNickname())){
//        if(board.getUser() != user || user.getRoll().eqauls("ADMIN")){
            return ResponseEntity.status(405).body(BaseResponseBody.of(405,  "No Authorization"));
        }

        comment.setComment(updateCommentDTO.getComment());
        commentRepoMongo.save(comment);
        return ResponseEntity.ok(BaseResponseBody.of(200, "Update Comment Success"));
    }

    public ResponseEntity deleteComment(String commentId) {
        User user = null;
        try {
            user = user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(BaseResponseBody.of(400, "No User"));
        }
        CommentMongo comment = null;
        try {
            comment = commentRepoMongo.findById(commentId).get();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(BaseResponseBody.of(400, "No Comment"));
        }

        if(!comment.getAuthor().equals(user.getNickname())){
//        if(board.getUser() != user || user.getRoll().eqauls("ADMIN")){
            return ResponseEntity.status(405).body(BaseResponseBody.of(405,  "No Authorization"));
        }

        commentRepoMongo.delete(comment);

        return ResponseEntity.ok(BaseResponseBody.of(200, "Delete Comment Success"));
    }
}
