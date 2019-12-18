package com.pcz.blog.repository;

import com.pcz.blog.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author picongzhi
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
