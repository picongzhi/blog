package com.pcz.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author picongzhi
 */
@Controller
@RequestMapping("/users")
public class UserController {
    @GetMapping
    public ModelAndView list() {
        return new ModelAndView("users/list");
    }
}
