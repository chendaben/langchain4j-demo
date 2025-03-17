package org.example.langchain4jdemo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.example.langchain4jdemo.entity.User;
import java.util.List;

@Mapper
public interface UserMapper {
    
    @Select("SELECT * FROM user WHERE id = #{id}")
    User selectById(@Param("id") Integer id);
    
    @Delete("<script>DELETE FROM user WHERE id IN <foreach collection='ids' item='id' open='(' separator=',' close=')'>#{id}</foreach></script>")
    int deleteBatchIds(@Param("ids") List<Integer> ids);
    
    @Insert("INSERT INTO user(name, birthday) VALUES(#{name}, #{birthday})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);
}
