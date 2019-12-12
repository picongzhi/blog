package com.pcz.blog.service;

import com.pcz.blog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author picongzhi
 */
public interface UserService {
    /**
     * 新增或更新用户
     *
     * @param user 用户信息
     * @return User
     */
    User saveOrUpdateUser(User user);

    /**
     * 注册用户
     *
     * @param user 用户信息
     * @return User
     */
    User registerUser(User user);

    /**
     * 删除用户
     *
     * @param id 用户id
     */
    void removeUser(Long id);

    /**
     * 根据id获取用户
     *
     * @param id 用户id
     * @return User
     */
    User getUserById(Long id);

    /**
     * 根据用户名分页模糊查询用户列表
     *
     * @param name     用户名
     * @param pageable 分页信息
     * @return Page<User>
     */
    Page<User> listUserByNameLike(String name, Pageable pageable);
}
