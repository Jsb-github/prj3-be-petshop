package com.example.prj3bepetshop.mapper;

import com.example.prj3bepetshop.domain.Event;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EventMapper {


    @Insert("""
                INSERT INTO event(
                    title, writer, fileName
                ) VALUES (
                #{title},#{writer},#{fileName}
                )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "no")
    int save(Event event);

    @Select("""
                SELECT  *
                FROM event
                order by no desc 
            """)
    List<Event> selectAll();

    @Select("""
                SELECT *
                FROM event
                WHERE no= #{no}            
            """)
    Event selectByEvent(Integer no);

    
    @Select("""
                SELECT fileName
                FROM event
                WHERE no = #{no}
            """)
    String selectByFileName(Integer no);


    @Delete("""
                DELETE FROM event
                WHERE no = #{no}
            """)
    boolean deleteByNo(Integer no);

    @Update("""
                UPDATE event
                SET 
                title = #{title},
                fileName = #{fileName}
                WHERE no = #{no}
                
            """)
    int update(Event event);
}
