package com.pcz.blog.service;

import com.pcz.blog.domain.Comment;

/**
 * @author picongzhi
 */
public interface CommentService {
    /**
     * 根据id获取评论
     *
     * @param id id
     * @return Comment
     */
    Comment getCommentById(Long id);

    /**
     * 根据id删除评论
     *
     * @param id id
     */
    void removeComment(Long id);
}
