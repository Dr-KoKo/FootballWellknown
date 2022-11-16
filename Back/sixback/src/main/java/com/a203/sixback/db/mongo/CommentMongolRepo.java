package com.a203.sixback.db.mongo;


import com.a203.sixback.db.entity.Comment;
import com.a203.sixback.db.entity.CommentMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentMongolRepo extends MongoRepository<CommentMongo, String> {

    List<CommentMongo> findAllByBoardId(long boardId);
}
