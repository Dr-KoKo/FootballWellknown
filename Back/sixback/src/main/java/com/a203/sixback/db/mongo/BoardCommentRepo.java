package com.a203.sixback.db.mongo;

import com.a203.sixback.db.entity.BoardComment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BoardCommentRepo extends MongoRepository<BoardComment, String> {
    BoardComment findByBoardId(Long boardId);

}
