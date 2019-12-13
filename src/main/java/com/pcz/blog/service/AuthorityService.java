package com.pcz.blog.service;

import com.pcz.blog.domain.Authority;

/**
 * @author picongzhi
 */
public interface AuthorityService {
    /**
     * 根据id查询权限
     *
     * @param id id
     * @return Authority
     */
    Authority getAuthorityById(Long id);
}
