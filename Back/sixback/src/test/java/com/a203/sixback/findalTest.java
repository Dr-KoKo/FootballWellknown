package com.a203.sixback;

import com.a203.sixback.db.mongo.CommentRepoMongoDB;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class findalTest {
    @Autowired
    private CommentRepoMongoDB commentRepoMongoDB;

    @Test
    public void printProjectData() {
        System.out.println(commentRepoMongoDB.findAll());
    }
}
