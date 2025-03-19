package org.example.langchain4jdemo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.langchain4jdemo.entity.UserRole;
import org.example.langchain4jdemo.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserRoleService extends ServiceImpl<UserRoleMapper, UserRole> {

    /**
     * 添加用户角色
     *
     * @param userRole 用户角色信息
     * @return 是否添加成功
     */
    @Transactional
    public boolean addUserRole(UserRole userRole) {
        return this.save(userRole);
    }
    
    /**
     * 获取用户角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    public List<UserRole> getUserRoles(Integer userId) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, userId);
        return this.list(queryWrapper);
    }
    
    /**
     * 检查用户是否有指定角色
     *
     * @param userId 用户ID
     * @param role   角色
     * @return 是否有该角色
     */
    public boolean hasRole(Integer userId, String role) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, userId)
                   .eq(UserRole::getRole, role);
        return this.count(queryWrapper) > 0;
    }
}
