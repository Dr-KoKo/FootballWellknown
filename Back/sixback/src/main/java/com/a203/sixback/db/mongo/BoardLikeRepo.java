package com.a203.sixback.db.mongo;

import com.a203.sixback.db.entity.BoardLike;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BoardLikeRepo  extends MongoRepository<BoardLike, String> {
    BoardLike findByBoardId(Long BoardId);
}
