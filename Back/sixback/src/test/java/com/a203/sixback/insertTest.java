package com.a203.sixback;

import com.a203.sixback.db.entity.CommentMongo;
import com.a203.sixback.db.mongo.CommentRepoMongoDB;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class insertTest {

    @Autowired
    CommentRepoMongoDB commentRepoMongoDB;

    @Test
    public void insertTest() {
        CommentMongo comment = CommentMongo.builder().boardId(2L).aurtor("AAA").comment("테스트").build();
        commentRepoMongoDB.save(comment);
//
        CommentMongo findComment = commentRepoMongoDB.findById(comment.getId()).get();

        System.out.println(findComment.getId());
        System.out.println(findComment.getAurtor());
        System.out.println(findComment.getBoardId());
        System.out.println(findComment.getComment());
    }
}
