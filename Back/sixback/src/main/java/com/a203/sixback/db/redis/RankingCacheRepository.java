package com.a203.sixback.db.redis;

import com.a203.sixback.db.enums.DayType;
import com.a203.sixback.ranking.res.ResponseRankingDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class RankingCacheRepository {

    private final RedisTemplate<String, String> rankingRedisTemplate;

    @Autowired
    public RankingCacheRepository(@Qualifier("rankRedisTemplate") RedisTemplate<String, String> rankingRedisTemplate) {
        this.rankingRedisTemplate = rankingRedisTemplate;
    }

    /**
     * @param value email
     * @param score 획득 점수
     */
    public void addScore(String value, double score) {

        for (DayType key : DayType.values()) {
            if (rankingRedisTemplate.opsForZSet().addIfAbsent(key.name(), value, score)) {
                continue;
            }

            rankingRedisTemplate.opsForZSet().incrementScore(key.name(), value, score);

            log.info("Add score to Redis {}:{}:{}", key.name(), value, score);
        }

    }

    public Long getRanking(DayType key, String value) {
        String _key = getKey(key);

        Long rank = rankingRedisTemplate.opsForZSet().reverseRank(_key, value) + 1;

        log.info("Get rank from Redis {}:{}", _key, value, rank);

        return rank;
    }

    public List<ResponseRankingDTO> getRankingList(DayType key) {
        String _key = getKey(key);

        Set<ZSetOperations.TypedTuple<String>> typedTuples = rankingRedisTemplate.opsForZSet().reverseRangeWithScores(_key, 0, 10);

        List<ResponseRankingDTO> collect = typedTuples.stream()
                .map(ResponseRankingDTO::convertToResponseRankingDto)
                .sorted(((o1, o2) -> (int) (o2.getScore() - o1.getScore())))
                .collect(Collectors.toList());

        return collect;
    }

    public void refreshDailyRanking(){
        String key = getKey(DayType.DAILY);
        rankingRedisTemplate.delete(key);
    }

    public void refreshWeeklyRanking(){
        String key = getKey(DayType.WEEKLY);
        rankingRedisTemplate.delete(key);
    }

    private String getKey(DayType dayType) {
        return dayType.name()+"RANKING";
    }

}
