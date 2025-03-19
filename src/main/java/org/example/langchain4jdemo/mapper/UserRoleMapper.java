package org.example.langchain4jdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.langchain4jdemo.entity.UserRole;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
}
