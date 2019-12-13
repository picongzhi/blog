package com.pcz.blog.repository;

import com.pcz.blog.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author picongzhi
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
