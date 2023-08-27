package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public Member save(Member member){
        /*
        Member id를 갱신 또는 삽일을 정함
        반환값은 id를 담아서 반환한다.

        */
        if(member.getId() == null){
            return insert(member);
        }
            return update(member);
        }


    public Member insert(Member member){
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
                //한번 검색해서 공부하기
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
var id =simpleJdbcInsert.executeAndReturnKey(params).longValue();
return Member.builder().id(id).email(member.getEmail()).nickname(member.getNickname()).birthday(member.getBirthday()).build();
    }
    public Member update(Member member){
    return member;
    }
}
