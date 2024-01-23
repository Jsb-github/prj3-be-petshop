package com.example.prj3bepetshop.mapper;

import com.example.prj3bepetshop.domain.Auth;
import com.example.prj3bepetshop.domain.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MemberMapper {
    
    @Insert("""
                INSERT INTO member(email, password) 
                VALUES (#{email}, #{password})
            """)
    int add(Member member);

    @Select("""
                SELECT email FROM member
                WHERE email = #{email}
            """)
    Integer checkEmail(String email);

    @Select("""
                SELECT * FROM member
                WHERE email = #{email}
            """)
    Member selectEmail(String email);

    @Select("""
                SELECT *
                FROM auth
                WHERE memberId = #{email}
            """)
    List<Auth> selectAuthById(String email);
}
