package com.example.bll;

import com.example.entity.UserInfo;
import com.example.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserInfoBll {

    @Autowired
    private UserInfoRepository userInfoRepository;

    public UserInfo findById(Integer id) {
        return this.userInfoRepository.findById(id);
    }

}
