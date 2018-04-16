package com.example.repository;

import com.example.entity.User;
import com.example.sqlProvider.UserSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface UserRepository {

    @SelectProvider(type = UserSqlProvider.class, method = "query")
    List<User> find(Map<String, Object> args);

    @SelectProvider(type = UserSqlProvider.class, method = "count")
    long count(Map<String, Object> args);

    @Insert("INSERT INTO user ( username, pwd, state, url)VALUES( #{username}, #{pwd}, #{state}, #{url})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(Integer id);

    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUserName(String userName);

    @Update("UPDATE user SET username = #{username}, pwd = #{pwd}, state = #{state}, url = #{url} WHERE id = #{id}")
    int update(User user);

    @Delete("DELETE FROM user WHERE id = #{id}")
    int delete(Integer id);

}
