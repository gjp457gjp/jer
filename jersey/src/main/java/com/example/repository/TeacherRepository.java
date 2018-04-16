package com.example.repository;

import com.example.entity.Teacher;
import com.example.sqlProvider.TeacherSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface TeacherRepository {

    @SelectProvider(type = TeacherSqlProvider.class, method = "query")
    List<Teacher> find(Map<String, Object> args);

    @SelectProvider(type = TeacherSqlProvider.class, method = "count")
    long count(Map<String, Object> args);

    @Insert("INSERT INTO teacher (tno, tname, qqnum, phone, pids) VALUES( #{tno}, #{tname}, #{qqnum}, #{phone}, #{pids})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Teacher teacher);

    @Select("SELECT * FROM teacher WHERE id = #{id}")
    Teacher findById(Integer id);

    @Select("SELECT * FROM teacher WHERE tno = #{tno}")
    Teacher findByTid(Integer tno);

    @Update("UPDATE teacher SET tno = #{tno}, tname = #{tname}, qqnum = #{qqnum}," +
            " phone = #{phone}, pids = #{pids} WHERE id = #{id}")
    int update(Teacher teacher);

    @Delete("DELETE FROM teacher WHERE id = #{id}")
    int delete(Integer id);

}
