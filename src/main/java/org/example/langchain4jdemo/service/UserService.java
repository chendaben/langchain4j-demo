package org.example.langchain4jdemo.service;

import org.example.langchain4jdemo.entity.User;
import org.example.langchain4jdemo.mapper.UserMapper;
import org.springframework.stereotype.Service;
import java.util.Collection;

@Service
public class UserService {
    
    private final UserMapper userMapper;
    
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    
    public User getUserById(Integer id) {
        return userMapper.selectById(id);
    }
    
    public int deleteUsersByIds(Collection<Integer> ids) {
        return userMapper.deleteBatchIds(ids);
    }
}
