package com.pcz.blog.service.impl;

import com.pcz.blog.domain.Authority;
import com.pcz.blog.repository.AuthorityRepository;
import com.pcz.blog.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author picongzhi
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {
    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public Authority getAuthorityById(Long id) {
        return authorityRepository.findOne(id);
    }
}
