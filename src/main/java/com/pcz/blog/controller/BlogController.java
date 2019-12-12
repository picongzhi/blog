package com.pcz.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author picongzhi
 */
@Controller
@RequestMapping("/blogs")
public class BlogController {
    @GetMapping
    public String listBlogs(@RequestParam(value = "order", defaultValue = "new") String order,
                            @RequestParam(value = "keyword", required = false) String keyword) {
        return "redirect:/index?order=" + order + "&keyword=" + keyword;
    }
}
