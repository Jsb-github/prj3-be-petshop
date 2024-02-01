package com.example.prj3bepetshop.mapper;

import com.example.prj3bepetshop.domain.ReviewFile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReviewFileMapper {


    @Insert("""
                INSERT INTO reviewfile(reviewNo, fileName) 
                VALUES (#{reviewNo},#{fileName})
            """)
    int insert(Integer reviewNo, String fileName);


    @Select("""
                SELECT *
                FROM reviewfile
                WHERE reviewNo = #{reviewNo}
                LIMIT 1
            """)
    List<ReviewFile> selectAllNamesById(Integer reviewNo);

    @Select("""
                SELECT *
                FROM reviewfile
                WHERE reviewNo =#{reviewNo}
            """)
    List<ReviewFile> selectNamesById(Integer no);


    @Delete("""
                DELETE FROM reviewfile
                WHERE reviewNo = #{reviewNo}
            """)
    int deleteFileByReviewNo(Integer reviewNo);


    @Select("""
                SELECT *
                FROM reviewfile
                WHERE no = #{no}
            """)
    ReviewFile selectById(Integer no);

    @Delete("""
                DELETE FROM reviewfile
                WHERE no = #{no}
            """)
    int deleteById(Integer no);
}
