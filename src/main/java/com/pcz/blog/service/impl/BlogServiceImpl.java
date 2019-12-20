package com.pcz.blog.service.impl;

import com.pcz.blog.domain.*;
import com.pcz.blog.repository.BlogRepository;
import com.pcz.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author picongzhi
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public void removeBlog(Long id) {
        blogRepository.delete(id);
    }

    @Transactional
    @Override
    public Blog updateBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public Blog getBlogById(Long id) {
        return blogRepository.findOne(id);
    }

    @Override
    public Page<Blog> listBlogsByTitleLike(User user, String title, Pageable pageable) {
        return blogRepository.findByUserAndTitleLikeOrderByCreateTimeDesc(user,
                "%" + title + "%", pageable);
    }

    @Override
    public Page<Blog> listBlogsByTitleLikeAndSort(User user, String title, Pageable pageable) {
        return blogRepository.findByUserAndTitleLike(user, "%" + title + "%", pageable);
    }

    @Override
    public Page<Blog> listBlogsByCatalog(Catalog catalog, Pageable pageable) {
        return blogRepository.findByCatalog(catalog, pageable);
    }

    @Override
    public void readingIncrease(Long id) {
        Blog blog = blogRepository.findOne(id);
        blog.setReadings(blog.getReadings() + 1);
        blogRepository.save(blog);
    }

    @Override
    public Blog createComment(Long blogId, String commentContent) {
        Blog blog = blogRepository.findOne(blogId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment comment = new Comment(user, commentContent);
        blog.addComment(comment);

        return blogRepository.save(blog);
    }

    @Override
    public void removeComment(Long blogId, Long commentId) {
        Blog blog = blogRepository.findOne(blogId);
        blog.removeComment(commentId);
        blogRepository.save(blog);
    }

    @Override
    public Blog createVote(Long blogId) {
        Blog blog = blogRepository.getOne(blogId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Vote vote = new Vote(user);

        boolean existed = blog.addVote(vote);
        if (existed) {
            throw new IllegalArgumentException("该用户已经点赞");
        }

        return blogRepository.save(blog);
    }

    @Override
    public void removeVote(Long blogId, Long voteId) {
        Blog blog = blogRepository.getOne(blogId);
        blog.removeVote(voteId);
        blogRepository.save(blog);
    }
}
