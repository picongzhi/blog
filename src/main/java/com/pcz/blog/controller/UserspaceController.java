package com.pcz.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author picongzhi
 */
@Controller
@RequestMapping("/u")
public class UserspaceController {
    @GetMapping("/{username}")
    public String userspace(@PathVariable("username") String username) {
        return "u";
    }

    @GetMapping("/{username}/blogs")
    public String listUserBlogs(@PathVariable("username") String username,
                                @RequestParam(value = "order", defaultValue = "new") String order,
                                @RequestParam(value = "category", required = false) Long category,
                                @RequestParam(value = "keyword", required = false) String keyword) {
        return "/u";
    }

    @GetMapping("/{username}/blogs/{id}")
    public String listUserBlogById(@PathVariable("username") String username,
                                   @PathVariable("id") Long id) {
        return "/blog";
    }

    @GetMapping("{username}/blogs/edit")
    public String editBlog(@PathVariable("username") String username) {
        return "/blogedit";
    }
}
