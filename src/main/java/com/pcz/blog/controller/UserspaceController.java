package com.pcz.blog.controller;

import com.pcz.blog.domain.User;
import com.pcz.blog.service.UserService;
import com.pcz.blog.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author picongzhi
 */
@Controller
@RequestMapping("/u")
public class UserspaceController {
    @Value("${file.server.url}")
    private String fileServerUrl;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public String userspace(@PathVariable("username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);

        return "redirect:/u/" + username + "/blogs";
    }

    @GetMapping("/{username}/profile")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView profile(@PathVariable("username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("fileServerUrl", fileServerUrl);

        return new ModelAndView("/userspace/profile", "userModel", model);
    }

    @PostMapping("/{username}/profile")
    @PreAuthorize("authentication.name.equals(#username)")
    public String saveProfile(@PathVariable("username") String username, User user) {
        User originalUser = userService.getUserById(user.getId());
        originalUser.setEmail(user.getEmail());
        originalUser.setName(user.getName());

        String rawPassword = originalUser.getPassword();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            originalUser.setPassword(user.getPassword());
        }

        userService.saveOrUpdateUser(user);

        return "redirect:/u/" + username + "/profile";
    }

    @GetMapping("/{username}/avatar")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView avatar(@PathVariable("username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);

        return new ModelAndView("/userspace/avatar", "userModel", model);
    }

    @PostMapping("/{username}/avatar")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> saveAvatar(@PathVariable("username") String username,
                                               @RequestBody User user) {
        String avatar = user.getAvatar();
        User originalUser = userService.getUserById(user.getId());
        originalUser.setAvatar(avatar);
        userService.saveOrUpdateUser(originalUser);

        return ResponseEntity.ok().body(new Response(true, "处理成功", avatar));
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
