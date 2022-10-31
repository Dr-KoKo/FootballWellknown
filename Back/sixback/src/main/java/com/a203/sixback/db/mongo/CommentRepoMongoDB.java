package com.a203.sixback.db.mongo;

import com.a203.sixback.db.entity.CommentMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepoMongoDB extends MongoRepository<CommentMongo, String> {

    List<CommentMongo> findAllByBoardId(Long boardId);

    List<CommentMongo> findAllByAuthorId(Long author_id);

}
