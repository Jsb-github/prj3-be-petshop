package com.example.prj3bepetshop.mapper;

import com.example.prj3bepetshop.domain.Notice;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoticeMapper {

    @Insert("""
            Insert into notice(title, writer, info) 
            values (#{title},#{writer},#{info})
            """)
    int save(Notice notice);

    @Select("""
                SELECT *
                FROM notice
                ORDER BY no DESC 
            """)
    List<Notice> selectAll();

    @Select("""
            SELECT *
            FROM notice
            WHERE no =#{no}
            """)
    Notice selectByNo(Integer no);

    @Update("""
                UPDATE notice
                SET 
                title = #{title},
                info = #{info}
               
                WHERE no =#{no}                                  
            """)
    int update(Notice notice);

    @Delete("""
                DELETE FROM notice
                WHERE no = #{no}
            """)
    int deleteByNo(Integer no);
}
