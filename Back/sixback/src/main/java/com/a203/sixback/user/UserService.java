package com.a203.sixback.user;

import com.a203.sixback.auth.UserPrincipal;
import com.a203.sixback.db.entity.*;
//import com.a203.sixback.db.mongo.CommentRepoMongoDB;
import com.a203.sixback.db.enums.DayType;
import com.a203.sixback.db.redis.RankingCacheRepository;
import com.a203.sixback.db.repo.BoardRepo;
import com.a203.sixback.db.repo.PointLogRepo;
import com.a203.sixback.db.repo.PredictRepo;
import com.a203.sixback.user.res.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final BoardRepo boardRepo;
    private final PointLogRepo pointLogRepo;
//    private final CommentRepoMongoDB commentRepoMongoDB;
    private final PredictRepo predictRepo;
    private final RankingCacheRepository rankingCacheRepository;

    @Autowired
    public UserService(BoardRepo boardRepo, PointLogRepo pointLogRepo, PredictRepo predictRepo, RankingCacheRepository rankingCacheRepository) {
        this.boardRepo = boardRepo;
        this.pointLogRepo = pointLogRepo;
//        this.commentRepoMongoDB = commentRepoMongoDB;
        this.predictRepo = predictRepo;
        this.rankingCacheRepository = rankingCacheRepository;
    }

    public ResGetUserDetailsDTO getUserDetails() {

        User user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        return ResGetUserDetailsDTO.of(200, "성공", user);
    }


    public ResGetUserBoardsDTO getUserBoards(int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("id").descending());

        User user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        List<ResGetUserBoardsDTO.GetBoardDTO> userBoardList = boardRepo.findAllByUser(user, pageRequest).stream()
                .map(board -> new ResGetUserBoardsDTO.GetBoardDTO(board, user, board.getMatch(), board.getTeam(), board.getCategory().getCtgName())).collect(Collectors.toList());

        return ResGetUserBoardsDTO.of(200, "성공", userBoardList);
    }

//    public ResGetUserCommentsDTO getUserComments() {
//
//        User user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
//
//        List<CommentMongo> commentMongoList = commentRepoMongoDB.findAllByAuthorId(user.getId());
//
//        return ResGetUserCommentsDTO.of(200, "성공", commentMongoList);
//    }

    public ResGetUserPredictsDTO getUserPredicts() {

        User user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        List<Predict> predictList = predictRepo.findAllByUser(user);

        return ResGetUserPredictsDTO.of(200, "성공", predictList);
    }

    public ResGetUserPointDTO getUserPoint(int page) {

        User user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("id").descending());

        List<ResGetUserPointDTO.GetPointDTO> pointLogList = pointLogRepo.findAllByUser(user, pageRequest).stream().map(ResGetUserPointDTO.GetPointDTO::new).collect(Collectors.toList());

        return ResGetUserPointDTO.of(200, "성공", pointLogList);
    }

    public ResGetUserRankDTO getUserRank() {

        User user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        Long rank = rankingCacheRepository.getRanking(DayType.ALL, user.getNickname());

        Long rankDaily = rankingCacheRepository.getRanking(DayType.DAILY, user.getNickname());

        Long rankWeekly = rankingCacheRepository.getRanking(DayType.WEEKLY, user.getNickname());

        return ResGetUserRankDTO.of(200, "성공", rank, rankDaily, rankWeekly);
    }
}
