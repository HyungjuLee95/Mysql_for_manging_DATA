package com.example.fastcampusmysql.domain.post.respository;

import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.entitiy.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepsitory {
    static final String Table = "Post";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    final static private RowMapper<DailyPostCount> DAILY_POST_COUNT_MAPPER =(ResultSet resultSet,int rowNum)-> new DailyPostCount(
            resultSet.getLong("memberId"),
            resultSet.getObject("createdDate", LocalDate.class),
            resultSet.getLong("count")
    );


    public List<DailyPostCount> groupBycreatedDate(DailyPostCountRequest request){
        String SQL = String.format("""
                SELECT memberId, createdDate, count(id) as count
                FROM %s
                WHERE memberId = :memberId and createdDate between :firstDate and :lastDate
                GROUP BY memberId, createdDate
                """, Table);
        //위 쿼리는 쿼리가 많아지면 분명히 성능 저하가 올것임.
        var params = new BeanPropertySqlParameterSource(request);
        return namedParameterJdbcTemplate.query(SQL, params,DAILY_POST_COUNT_MAPPER);
    }


    public Post save(Post post){
        if(post.getId() == null){
            return insert(post);

        }
        throw new UnsupportedOperationException("Post는 갱신을 지원하지 않습니다.");
    }

    private Post insert(Post post) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("Post")
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new BeanPropertySqlParameterSource(post);
        var id = jdbcInsert.executeAndReturnKey(params).longValue();

        return Post.builder()
                .id(id)
                .memberId(post.getMemberId())
                .contents(post.getContents())
                .createdAt(post.getCreatedAt())
                .createDate(post.getCreatedDate())
                .build();

    }



}
