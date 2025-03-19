package org.example.langchain4jdemo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.langchain4jdemo.entity.Pet;
import org.example.langchain4jdemo.mapper.PetMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 宠物服务类
 */
@Service
public class PetService extends ServiceImpl<PetMapper, Pet> {

    /**
     * 分页查询宠物列表
     *
     * @param page 页码
     * @param size 每页大小
     * @return 宠物分页结果
     */
    public Page<Pet> getPets(Integer page, Integer size) {
        Page<Pet> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Pet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Pet::getCreateTime);
        return this.page(pageParam, queryWrapper);
    }

    /**
     * 根据ID获取宠物详情
     *
     * @param id 宠物ID
     * @return 宠物详情
     */
    public Pet getPetById(Integer id) {
        return this.getById(id);
    }

    /**
     * 添加宠物
     *
     * @param pet 宠物信息
     * @return 宠物ID
     */
    @Transactional
    public Integer addPet(Pet pet) {
        pet.setCreateTime(LocalDateTime.now());
        pet.setUpdateTime(LocalDateTime.now());
        if (pet.getStatus() == null) {
            pet.setStatus("LOST");
        }
        this.save(pet);
        return pet.getId();
    }

    /**
     * 更新宠物信息
     *
     * @param pet 宠物信息
     * @return 是否更新成功
     */
    @Transactional
    public boolean updatePet(Pet pet) {
        pet.setUpdateTime(LocalDateTime.now());
        return this.updateById(pet);
    }

    /**
     * 删除宠物
     *
     * @param id 宠物ID
     * @return 是否删除成功
     */
    @Transactional
    public boolean deletePet(Integer id) {
        return this.removeById(id);
    }

    /**
     * 标记宠物为已找回
     *
     * @param id 宠物ID
     * @return 是否标记成功
     */
    @Transactional
    public boolean markPetAsFound(Integer id) {
        Pet pet = this.getById(id);
        if (pet != null) {
            pet.setStatus("FOUND");
            pet.setUpdateTime(LocalDateTime.now());
            return this.updateById(pet);
        }
        return false;
    }

    /**
     * 搜索宠物
     *
     * @param keyword 关键词
     * @param page    页码
     * @param size    每页大小
     * @return 宠物分页结果
     */
    public Page<Pet> searchPets(String keyword, Integer page, Integer size) {
        Page<Pet> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Pet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Pet::getName, keyword)
                .or().like(Pet::getType, keyword)
                .or().like(Pet::getBreed, keyword)
                .or().like(Pet::getLostLocation, keyword);
        queryWrapper.orderByDesc(Pet::getCreateTime);
        return this.page(pageParam, queryWrapper);
    }

    /**
     * 按条件筛选宠物
     *
     * @param type        宠物类型
     * @param status      状态
     * @param startTime   走失开始时间
     * @param endTime     走失结束时间
     * @param page        页码
     * @param size        每页大小
     * @return 宠物分页结果
     */
    public Page<Pet> filterPets(String type, String status, LocalDateTime startTime, 
                               LocalDateTime endTime, Integer page, Integer size) {
        Page<Pet> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Pet> queryWrapper = new LambdaQueryWrapper<>();
        
        if (type != null && !type.isEmpty()) {
            queryWrapper.eq(Pet::getType, type);
        }
        
        if (status != null && !status.isEmpty()) {
            queryWrapper.eq(Pet::getStatus, status);
        }
        
        if (startTime != null) {
            queryWrapper.ge(Pet::getLostTime, startTime);
        }
        
        if (endTime != null) {
            queryWrapper.le(Pet::getLostTime, endTime);
        }
        
        queryWrapper.orderByDesc(Pet::getCreateTime);
        return this.page(pageParam, queryWrapper);
    }

    /**
     * 获取用户的宠物列表
     *
     * @param ownerId 主人ID
     * @return 宠物列表
     */
    public List<Pet> getPetsByOwnerId(Integer ownerId) {
        LambdaQueryWrapper<Pet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Pet::getOwnerId, ownerId);
        queryWrapper.orderByDesc(Pet::getCreateTime);
        return this.list(queryWrapper);
    }
}
