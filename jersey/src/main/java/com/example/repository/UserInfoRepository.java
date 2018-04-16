package com.example.repository;

import com.example.entity.UserInfo;
import org.apache.ibatis.annotations.*;


public interface UserInfoRepository {
    @Select("SELECT * FROM userInfo WHERE id = #{id}")
    UserInfo findById(Integer id);
}
