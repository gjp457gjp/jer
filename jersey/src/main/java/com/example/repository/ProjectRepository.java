package com.example.repository;

import com.example.entity.Project;
import com.example.sqlProvider.ProjectSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface ProjectRepository {

    @SelectProvider(type = ProjectSqlProvider.class, method = "query")
    List<Project> find(Map<String, Object> args);

    @SelectProvider(type = ProjectSqlProvider.class, method = "count")
    long count(Map<String, Object> args);

    @Insert("INSERT INTO project (title, pdesc, util, tid, sid, num, score)" +
            "VALUES( #{title}, #{pdesc}, #{util}, #{tid}, #{sid}, #{num}, #{score})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Project project);

    @Select("SELECT * FROM project WHERE id = #{id}")
    Project findById(Integer id);

    @Update("UPDATE project SET title = #{title}, pdesc = #{pdesc}, util = #{util}," +
            " tid = #{tid}, sid = #{sid}, num = #{num}," +
            " score = #{score} WHERE id = #{id}")
    int update(Project project);

    @Delete("DELETE FROM project WHERE id = #{id}")
    int delete(Integer id);

}
