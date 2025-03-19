package org.example.langchain4jdemo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.langchain4jdemo.entity.CommentAttachment;
import org.example.langchain4jdemo.mapper.CommentAttachmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 评论附件服务类
 */
@Service
public class CommentAttachmentService extends ServiceImpl<CommentAttachmentMapper, CommentAttachment> {

    @Autowired
    private FileService fileService;

    /**
     * 根据评论ID获取附件列表
     *
     * @param commentId 评论ID
     * @return 附件列表
     */
    public List<CommentAttachment> getAttachmentsByCommentId(Integer commentId) {
        LambdaQueryWrapper<CommentAttachment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentAttachment::getCommentId, commentId);
        return this.list(queryWrapper);
    }

    /**
     * 上传评论附件
     *
     * @param commentId 评论ID
     * @param files     文件列表
     * @return 上传成功的附件数量
     */
    @Transactional
    public int uploadAttachments(Integer commentId, MultipartFile[] files) throws IOException {
        int count = 0;
        for (MultipartFile file : files) {
            String filePath = fileService.storeFile(file);
            
            CommentAttachment attachment = new CommentAttachment();
            attachment.setCommentId(commentId);
            attachment.setFileName(file.getOriginalFilename());
            attachment.setFilePath(filePath);
            attachment.setFileType(file.getContentType());
            attachment.setFileSize(file.getSize());
            
            if (this.save(attachment)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 删除评论附件
     *
     * @param id 附件ID
     * @return 是否删除成功
     */
    @Transactional
    public boolean deleteAttachment(Integer id) {
        CommentAttachment attachment = this.getById(id);
        if (attachment != null) {
            fileService.deleteFile(attachment.getFilePath());
            return this.removeById(id);
        }
        return false;
    }

    /**
     * 根据评论ID删除所有附件
     *
     * @param commentId 评论ID
     * @return 删除的附件数量
     */
    @Transactional
    public int deleteAttachmentsByCommentId(Integer commentId) {
        List<CommentAttachment> attachments = getAttachmentsByCommentId(commentId);
        for (CommentAttachment attachment : attachments) {
            fileService.deleteFile(attachment.getFilePath());
        }
        
        LambdaQueryWrapper<CommentAttachment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentAttachment::getCommentId, commentId);
        return this.count(queryWrapper) > 0 ? (this.remove(queryWrapper) ? attachments.size() : 0) : 0;
    }
}
