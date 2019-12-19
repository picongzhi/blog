package com.pcz.blog.repository;

import com.pcz.blog.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author picongzhi
 */
public interface VoteRepository extends JpaRepository<Vote, Long> {
}
