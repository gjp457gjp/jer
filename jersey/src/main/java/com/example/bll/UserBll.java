package com.example.bll;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UserBll {

    @Autowired
    private UserRepository userRepository;

    public List<User> find(Map<String, Object> args) {
        return this.userRepository.find(args);
    }

    public long count(Map<String, Object> args) {
        return this.userRepository.count(args);
    }

    public int insert(User user) {
        return this.userRepository.insert(user);
    }

    public User findById(Integer id) {
        return this.userRepository.findById(id);
    }

    public User findByUserName(String userName) {
        return this.userRepository.findByUserName(userName);
    }

    public int update(User user) {
        return this.userRepository.update(user);
    }

    public int delete(Integer id) {
        return this.userRepository.delete(id);
    }
}
