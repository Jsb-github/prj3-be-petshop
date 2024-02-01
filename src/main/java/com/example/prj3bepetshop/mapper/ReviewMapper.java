package com.example.prj3bepetshop.mapper;

import com.example.prj3bepetshop.domain.Review;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReviewMapper {

    @Select("""
                SELECT rv.no, rv.title, rv.info, rv.writer, rv.point, rv.inserted
                FROM review rv left join member mb
                ON rv.writer = mb.email
                WHERE rv.no = #{no}
            """)
    Review selectByNo(Integer no);


    @Insert("""
                INSERT INTO review(title, info, writer, point) 
                VALUES (
                    #{title},#{info},#{writer},#{point}
                )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "no")
    int save(Review review);

    @Select("""
                SELECT *
                FROM review
            """)
    List<Review> selectAll();


    @Delete("""
                DELETE FROM review
                WHERE no = #{no}
            """)
    int deleteByNo(Integer no);


    @Select("""
                SELECT COUNT(rf.no)
                FROM review rv LEFT JOIN reviewfile rf
                ON rv.no = rf.reviewNo
                WHERE reviewNo = #{reviewNo}
            """)
    Integer selectFileCount(Integer reviewNo);


    @Update("""
                UPDATE review
                SET 
                title = #{title},
                info = #{info},
                point = #{point}
                WHERE no = #{no}
            """)
    int update(Review review);


    @Select("""
                SELECT consumer
                FROM ordergoods
                WHERE no = #{no}
            """)
    boolean selectWriteByNo(Integer no);
}
