package com.pcz.blog.repository;

import com.pcz.blog.domain.Blog;
import com.pcz.blog.domain.Catalog;
import com.pcz.blog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author picongzhi
 */
public interface BlogRepository extends JpaRepository<Blog, Long> {
    /**
     * 根据用户名、博客标题分页查询博客列表（逆序）
     *
     * @param user     用户
     * @param title    标题
     * @param pageable 分页信息
     * @return Page<Blog>
     */
    Page<Blog> findByUserAndTitleLikeOrderByCreateTimeDesc(User user, String title, Pageable pageable);

    /**
     * 根据用户名、博客标题分页查询博客列表
     *
     * @param user     用户
     * @param title    标题
     * @param pageable 分页信息
     * @return
     */
    Page<Blog> findByUserAndTitleLike(User user, String title, Pageable pageable);

    /**
     * 根据分类分页查询博客
     *
     * @param catalog  分类
     * @param pageable 分页信息
     * @return Page<Blog>
     */
    Page<Blog> findByCatalog(Catalog catalog, Pageable pageable);

    /**
     * 根据用户和标题模糊查询或者根据用户和标签模糊查询按照创建时间倒序
     *
     * @param title    标题
     * @param user     用户
     * @param tags     标签
     * @param user2    用户
     * @param pageable 分页信息
     * @return Page<Blog>
     */
    Page<Blog> findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(String title, User user, String tags, User user2, Pageable pageable);
}
