package com.pcz.blog.service;

import com.pcz.blog.domain.Blog;
import com.pcz.blog.domain.Catalog;
import com.pcz.blog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author picongzhi
 */
public interface BlogService {
    /**
     * 保存Blog
     *
     * @param blog Blog
     * @return Blog
     */
    Blog saveBlog(Blog blog);

    /**
     * 删除Blog
     *
     * @param id id
     */
    void removeBlog(Long id);

    /**
     * 更新Blog
     *
     * @param blog Blog
     * @return Blog
     */
    Blog updateBlog(Blog blog);

    /**
     * 根据id获取Blog
     *
     * @param id id
     * @return Blog
     */
    Blog getBlogById(Long id);

    /**
     * 根据用户名对标题进行分页模糊查询
     *
     * @param user     用户
     * @param title    标题
     * @param pageable 分页信息
     * @return Page<Blog>
     */
    Page<Blog> listBlogsByTitleLike(User user, String title, Pageable pageable);

    /**
     * 根据用户名对标题进行分页模糊查询
     *
     * @param user     用户
     * @param title    标题
     * @param pageable 分页信息
     * @return Page<Blog>
     */
    Page<Blog> listBlogsByTitleLikeAndSort(User user, String title, Pageable pageable);

    /**
     * 根据分类分页查询
     *
     * @param catalog  分类
     * @param pageable 分页信息
     * @return Page<Blog>
     */
    Page<Blog> listBlogsByCatalog(Catalog catalog, Pageable pageable);

    /**
     * 增加阅读量
     *
     * @param id id
     */
    void readingIncrease(Long id);

    /**
     * 发表评论
     *
     * @param blogId         博客id
     * @param commentContent 评论内容
     * @return Blog
     */
    Blog createComment(Long blogId, String commentContent);

    /**
     * 删除评论
     *
     * @param blogId    博客id
     * @param commentId 评论id
     */
    void removeComment(Long blogId, Long commentId);

    /**
     * 点赞
     *
     * @param blogId 博客id
     * @return Blog
     */
    Blog createVote(Long blogId);

    /**
     * 取消点赞
     *
     * @param blogId 博客id
     * @param voteId 点赞id
     */
    void removeVote(Long blogId, Long voteId);
}
