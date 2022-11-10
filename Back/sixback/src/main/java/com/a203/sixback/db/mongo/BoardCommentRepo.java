package com.a203.sixback.db.mongo;

import com.a203.sixback.board.dto.GetCommentResDTO;
import com.a203.sixback.db.entity.BoardComment;
import com.a203.sixback.db.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Map;

public interface BoardCommentRepo extends MongoRepository<BoardComment, String> {
    BoardComment findByBoardId(Long boardId);

}
