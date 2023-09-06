package com.example.fastcampusmysql.domain.post;


import com.example.fastcampusmysql.domain.post.entitiy.Post;
import com.example.fastcampusmysql.domain.post.respository.PostRepsitory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.stream.IntStream;

@SpringBootTest
public class PostBulkInsertTest {
    @Autowired
    private PostRepsitory postRepsitory;

    @Test
    public void bulkInsert(){
        var easyRandom = PostFixtureFactory.get(3L,
                LocalDate.of(2023, 9,3),
                LocalDate.of(2023, 9,6)

        );

        IntStream.range(0, 10)
                .mapToObj(i->easyRandom.nextObject(Post.class) )
                .forEach(x->
                        postRepsitory.save(x)
                );


    }
}
