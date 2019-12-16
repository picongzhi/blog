package com.pcz.blog.repository;

import com.pcz.blog.domain.Blog;
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
    Page<Blog> findByUserAndTitleLikeOrOrderByCreateTimeDesc(User user, String title, Pageable pageable);

    /**
     * 根据用户名、博客标题分页查询博客列表
     *
     * @param user     用户
     * @param title    标题
     * @param pageable 分页信息
     * @return
     */
    Page<Blog> findByUserAndTitleLike(User user, String title, Pageable pageable);
}
