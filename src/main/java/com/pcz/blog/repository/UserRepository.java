package com.pcz.blog.repository;

import com.pcz.blog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author picongzhi
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 根据用户姓名模糊查询用户列表
     *
     * @param name     姓名
     * @param pageable 分页参数
     * @return Page<User>
     */
    Page<User> findByNameLike(String name, Pageable pageable);

    /**
     * 根据用户账号查询用户
     *
     * @param username 用户账号
     * @return User
     */
    User findByUsername(String username);
}
