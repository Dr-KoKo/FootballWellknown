package com.a203.sixback.ranking;

import com.a203.sixback.db.entity.*;
import com.a203.sixback.db.enums.DayType;
import com.a203.sixback.db.redis.RankingCacheRepository;
import com.a203.sixback.db.repo.PointLogRepo;
import com.a203.sixback.db.repo.UserRepo;
import com.a203.sixback.ranking.res.ResponseRankingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final UserRepo userRepo;
    private final PointLogRepo pointLogRepo;
    private final RankingCacheRepository rankingCacheRepository;

    @Transactional
    public void addScore(User user, Predict mp, PlayerEvaluate pe, int point) {
        LocalDateTime now = LocalDateTime.now();

        PointLog pointLog = PointLog.builder()
                .user(user)
                .distribute_time(now)
                .point(point)
                .mp(mp)
                .pe(pe)
                .build();

        pointLogRepo.save(pointLog);

        user.setPoint(user.getPoint() + point);
        userRepo.save(user);

        rankingCacheRepository.addScore(user.getNickname(), user.getPoint());
    }

    @Transactional
    public void addAllScore(List<Predict> correctPredictList, int point) {

        LocalDateTime now = LocalDateTime.now();

        List<PointLog> pointLogList = new ArrayList<>(correctPredictList.size());
        List<User> userList = new ArrayList<>(correctPredictList.size());

        for (Predict predict : correctPredictList) {
            User user = predict.getUser();
            user.setPoint(user.getPoint() + point);
            userList.add(user);

            PointLog log = PointLog.builder()
                    .user(user)
                    .mp(predict)
                    .point(point)
                    .distribute_time(now)
                    .build();
            pointLogList.add(log);

            rankingCacheRepository.addScore(user.getNickname(), user.getPoint());
        }

        pointLogRepo.saveAll(pointLogList);

        userRepo.saveAll(userList);

    }

    public Long getRanking(User user){
        return rankingCacheRepository.getRanking(DayType.ALL, user.getEmail());
    }

    public Long getDailyRanking(User user){
        return rankingCacheRepository.getRanking(DayType.DAILY, user.getEmail());
    }

    public Long getWeeklyRanking(User user){
        return rankingCacheRepository.getRanking(DayType.WEEKLY, user.getEmail());
    }

    public List<ResponseRankingDTO> getRankingList(){
        return rankingCacheRepository.getRankingList(DayType.ALL);
    }

    public List<ResponseRankingDTO> getDailyRankingList(){
        return rankingCacheRepository.getRankingList(DayType.DAILY);
    }

    public List<ResponseRankingDTO> getWeeklyRankingList(){
        return rankingCacheRepository.getRankingList(DayType.WEEKLY);
    }

    @Transactional
    public void refreshDailyRanking(){
        rankingCacheRepository.refreshDailyRanking();
    }

    @Transactional
    public void refreshWeeklyRanking(){
        rankingCacheRepository.refreshWeeklyRanking();
    }

}
