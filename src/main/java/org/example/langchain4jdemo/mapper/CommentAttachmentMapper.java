package org.example.langchain4jdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.langchain4jdemo.entity.CommentAttachment;

/**
 * 评论附件Mapper接口
 */
@Mapper
public interface CommentAttachmentMapper extends BaseMapper<CommentAttachment> {
}
