package com.a203.sixback.db.mongo;


import com.a203.sixback.db.entity.CommentMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentMongolRepo extends MongoRepository<CommentMongo, String> {


}
