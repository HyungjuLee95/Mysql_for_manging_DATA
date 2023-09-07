package com.example.fastcampusmysql.domain.post;


import com.example.fastcampusmysql.domain.post.entitiy.Post;
import com.example.fastcampusmysql.domain.post.respository.PostRepsitory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

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
        var stopWatch = new StopWatch();
        stopWatch.start();

       var posts =  IntStream.range(0, 1000000)
               .parallel()
                .mapToObj(i->easyRandom.nextObject(Post.class) )
                .toList();
        stopWatch.stop();
        System.out.println("객체 생성 시간 : " + stopWatch.getTotalTimeSeconds());

        var queryStopwatch = new StopWatch();
        queryStopwatch.start();

        postRepsitory.bulkInsert(posts);
        queryStopwatch.stop();

        System.out.println("쿼리 생성 시간 : " + queryStopwatch.getTotalTimeSeconds());


    }
}
