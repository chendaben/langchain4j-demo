package org.example.langchain4jdemo.service;

import org.example.langchain4jdemo.entity.User;
import org.example.langchain4jdemo.mapper.UserMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    
    private final UserMapper userMapper;
    
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    
    public User getUserById(Integer id) {
        return userMapper.selectById(id);
    }
    
    public int deleteUsersByIds(List<Integer> ids) {
        return userMapper.deleteBatchIds(ids);
    }
    
    public User createUser(User user) {
        userMapper.insert(user);
        return user;
    }
}
