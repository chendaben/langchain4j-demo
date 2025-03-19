package org.example.langchain4jdemo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Options;
import org.example.langchain4jdemo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    
    @Select("SELECT * FROM \"user\" WHERE id = #{id}")
    User selectById(@Param("id") Integer id);
    
    @Delete("<script>DELETE FROM \"user\" WHERE id IN <foreach collection='ids' item='id' open='(' separator=',' close=')'>#{id}</foreach></script>")
    int deleteBatchIds(@Param("ids") List<Integer> ids);
    
    @Insert("INSERT INTO \"user\"(username, password, name, email, phone, last_login_time, create_time) VALUES(#{username}, #{password}, #{name}, #{email}, #{phone}, #{lastLoginTime}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);
    
    @Select("SELECT COUNT(*) FROM \"user\" WHERE username = #{username}")
    long countByUsername(@Param("username") String username);
    
    @Select("SELECT * FROM \"user\" WHERE username = #{username} LIMIT 1")
    User findByUsername(@Param("username") String username);
    
    @Update("UPDATE \"user\" SET username=#{username}, password=#{password}, name=#{name}, email=#{email}, phone=#{phone}, last_login_time=#{lastLoginTime}, update_time=#{updateTime} WHERE id=#{id}")
    int updateById(User user);
}
