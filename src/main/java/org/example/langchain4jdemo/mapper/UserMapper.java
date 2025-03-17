package org.example.langchain4jdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.langchain4jdemo.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    // BaseMapper provides CRUD methods including selectById
}
