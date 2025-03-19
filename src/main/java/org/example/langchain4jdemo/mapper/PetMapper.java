package org.example.langchain4jdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.langchain4jdemo.entity.Pet;

/**
 * 宠物Mapper接口
 */
@Mapper
public interface PetMapper extends BaseMapper<Pet> {
}
