package com.a203.sixback.user;

import com.a203.sixback.auth.UserPrincipal;
import com.a203.sixback.db.entity.Board;
import com.a203.sixback.db.entity.Predict;
import com.a203.sixback.db.entity.User;
//import com.a203.sixback.db.mongo.CommentRepoMongoDB;
import com.a203.sixback.db.repo.BoardRepo;
import com.a203.sixback.db.repo.PredictRepo;
import com.a203.sixback.user.res.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final BoardRepo boardRepo;
//    private final CommentRepoMongoDB commentRepoMongoDB;
    private final PredictRepo predictRepo;

//    public UserService(BoardRepo boardRepo, CommentRepoMongoDB commentRepoMongoDB, PredictRepo predictRepo) {
//        this.boardRepo = boardRepo;
//        this.commentRepoMongoDB = commentRepoMongoDB;
//        this.predictRepo = predictRepo;
//    }

    public ResGetUserDetailsDTO getUserDetails() {

//        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        User user = ((UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        return ResGetUserDetailsDTO.of(200,"성공", user);
    }


    public ResGetUserBoardsDTO getUserBoards() {

        User user = ((UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

//        List<Board> boardList = boardRepo.findAllByUser(user);

        List<ResGetUserBoardsDTO.GetBoardDTO> userBoardList = boardRepo.findAllByUser(user).stream()
                .map(board->new ResGetUserBoardsDTO.GetBoardDTO(board,user,board.getMatch().getId())).collect(Collectors.toList());

//        for (Board board : boardList) {
//            userBoardList.add(new ResGetUserBoardsDTO.GetBoardDTO(board,user,board.getMatch().getId()));
//        }

        return ResGetUserBoardsDTO.of(200, "성공", userBoardList);
    }

//    public ResGetUserCommentsDTO getUserComments() {
//
//        User user = ((UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
//
//        List<CommentMongo> commentMongoList = commentRepoMongoDB.findAllByUserId(user.getId());
//
//        return ResGetUserCommentsDTO.of(200, "성공", commentMongoList);
//    }

    public ResGetUserPredictsDTO getUserPredicts() {

        User user = ((UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        List<Predict> predictList = predictRepo.findAllByUser(user);

        return ResGetUserPredictsDTO.of(200,"성공", predictList);
    }

    public ResGetUserPointDTO getUserPoint() {

        User user = ((UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        return ResGetUserPointDTO.of(200,"성공", user.getPoint());
    }
}
