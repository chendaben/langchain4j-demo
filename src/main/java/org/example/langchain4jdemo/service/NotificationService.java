package org.example.langchain4jdemo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.langchain4jdemo.entity.Comment;
import org.example.langchain4jdemo.entity.Notification;
import org.example.langchain4jdemo.entity.Pet;
import org.example.langchain4jdemo.mapper.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 通知服务类
 */
@Service
public class NotificationService extends ServiceImpl<NotificationMapper, Notification> {

    @Autowired
    private PetService petService;

    /**
     * 创建评论通知
     *
     * @param comment 评论信息
     */
    @Transactional
    public void createCommentNotification(Comment comment) {
        Pet pet = petService.getPetById(comment.getPetId());
        if (pet != null && pet.getOwnerId() != null) {
            Notification notification = new Notification();
            notification.setUserId(pet.getOwnerId());
            notification.setTitle("您的宠物有新的线索评论");
            notification.setContent("您的宠物 " + pet.getName() + " 有新的线索评论，请及时查看。");
            notification.setType("COMMENT");
            notification.setRelatedId(comment.getId());
            notification.setIsRead(false);
            notification.setCreateTime(LocalDateTime.now());
            this.save(notification);
        }
    }

    /**
     * 创建系统通知
     *
     * @param userId  用户ID
     * @param title   通知标题
     * @param content 通知内容
     * @return 通知ID
     */
    @Transactional
    public Integer createSystemNotification(Integer userId, String title, String content) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setTitle(title);
        notification.setContent(content);
        notification.setType("SYSTEM");
        notification.setIsRead(false);
        notification.setCreateTime(LocalDateTime.now());
        this.save(notification);
        return notification.getId();
    }

    /**
     * 批量创建系统通知
     *
     * @param userIds 用户ID列表
     * @param title   通知标题
     * @param content 通知内容
     * @return 创建的通知数量
     */
    @Transactional
    public int createSystemNotifications(List<Integer> userIds, String title, String content) {
        int count = 0;
        for (Integer userId : userIds) {
            Notification notification = new Notification();
            notification.setUserId(userId);
            notification.setTitle(title);
            notification.setContent(content);
            notification.setType("SYSTEM");
            notification.setIsRead(false);
            notification.setCreateTime(LocalDateTime.now());
            if (this.save(notification)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 获取用户的通知列表
     *
     * @param userId 用户ID
     * @param page   页码
     * @param size   每页大小
     * @return 通知分页结果
     */
    public Page<Notification> getUserNotifications(Integer userId, Integer page, Integer size) {
        Page<Notification> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Notification> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Notification::getUserId, userId);
        queryWrapper.orderByDesc(Notification::getCreateTime);
        return this.page(pageParam, queryWrapper);
    }

    /**
     * 获取用户的未读通知数量
     *
     * @param userId 用户ID
     * @return 未读通知数量
     */
    public int getUnreadNotificationCount(Integer userId) {
        LambdaQueryWrapper<Notification> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Notification::getUserId, userId);
        queryWrapper.eq(Notification::getIsRead, false);
        return this.count(queryWrapper);
    }

    /**
     * 标记通知为已读
     *
     * @param id 通知ID
     * @return 是否标记成功
     */
    @Transactional
    public boolean markAsRead(Integer id) {
        Notification notification = this.getById(id);
        if (notification != null) {
            notification.setIsRead(true);
            return this.updateById(notification);
        }
        return false;
    }

    /**
     * 标记用户所有通知为已读
     *
     * @param userId 用户ID
     * @return 标记的通知数量
     */
    @Transactional
    public int markAllAsRead(Integer userId) {
        List<Notification> notifications = this.lambdaQuery()
                .eq(Notification::getUserId, userId)
                .eq(Notification::getIsRead, false)
                .list();
        
        for (Notification notification : notifications) {
            notification.setIsRead(true);
            this.updateById(notification);
        }
        
        return notifications.size();
    }

    /**
     * 删除通知
     *
     * @param id 通知ID
     * @return 是否删除成功
     */
    @Transactional
    public boolean deleteNotification(Integer id) {
        return this.removeById(id);
    }
}
