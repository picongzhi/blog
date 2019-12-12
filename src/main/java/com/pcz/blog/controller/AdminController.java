package com.pcz.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author picongzhi
 */
@Controller
@RequestMapping("/admins")
public class AdminController {
    @GetMapping
    public ModelAndView listUsers(Model model) {
        return new ModelAndView("admins/index", "menuList", model);
    }
}
