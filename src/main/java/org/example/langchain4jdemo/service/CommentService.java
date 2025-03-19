package org.example.langchain4jdemo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.langchain4jdemo.entity.Comment;
import org.example.langchain4jdemo.mapper.CommentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 评论服务类
 */
@Service
public class CommentService extends ServiceImpl<CommentMapper, Comment> {

    /**
     * 根据宠物ID分页查询评论
     *
     * @param petId 宠物ID
     * @param page  页码
     * @param size  每页大小
     * @return 评论分页结果
     */
    public Page<Comment> getCommentsByPetId(Integer petId, Integer page, Integer size) {
        Page<Comment> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getPetId, petId);
        queryWrapper.orderByDesc(Comment::getCreateTime);
        return this.page(pageParam, queryWrapper);
    }

    /**
     * 添加评论
     *
     * @param comment 评论信息
     * @return 评论ID
     */
    @Transactional
    public Integer addComment(Comment comment) {
        this.save(comment);
        return comment.getId();
    }

    /**
     * 删除评论
     *
     * @param id     评论ID
     * @param userId 用户ID（用于权限验证）
     * @return 是否删除成功
     */
    @Transactional
    public boolean deleteComment(Integer id, Integer userId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getId, id);
        queryWrapper.eq(Comment::getUserId, userId);
        return this.remove(queryWrapper);
    }

    /**
     * 管理员删除评论
     *
     * @param id 评论ID
     * @return 是否删除成功
     */
    @Transactional
    public boolean deleteCommentByAdmin(Integer id) {
        return this.removeById(id);
    }

    /**
     * 标记评论为有用
     *
     * @param id 评论ID
     * @return 是否标记成功
     */
    public boolean markCommentAsUseful(Integer id) {
        Comment comment = this.getById(id);
        if (comment != null) {
            comment.setIsUseful(true);
            return this.updateById(comment);
        }
        return false;
    }
}
