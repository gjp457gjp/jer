package com.example.repository;

import com.example.entity.Student;
import com.example.sqlProvider.StudentSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface StudentRepository {

    @SelectProvider(type = StudentSqlProvider.class, method = "query")
    List<Student> find(Map<String, Object> args);

    @SelectProvider(type = StudentSqlProvider.class, method = "count")
    long count(Map<String, Object> args);

    @Insert("INSERT INTO student (sno, sname, age, sex, major, qqnum, phone, pid)" +
            "VALUES( #{sno}, #{sname}, #{age}, #{sex}, #{major}, #{qqnum}, #{phone}, #{pid})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Student student);

    @Select("SELECT * FROM student WHERE id = #{id}")
    Student findById(Integer id);

    @Select("SELECT * FROM student WHERE sno = #{sno}")
    Student findBySid(Long sno);

    @Select("SELECT * FROM student WHERE sname = #{sname}")
    Student findBySname(String sname);

    @Update("UPDATE student SET sno = #{sno}, sname = #{sname}, age = #{age}," +
            " sex = #{sex}, major = #{major}, qqnum = #{qqnum}," +
            " phone = #{phone}, pid = #{pid} WHERE id = #{id}")
    int update(Student student);

    @Delete("DELETE FROM student WHERE id = #{id}")
    int delete(Integer id);

}
