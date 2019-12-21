package com.pcz.blog.controller;

import com.pcz.blog.domain.Blog;
import com.pcz.blog.domain.Catalog;
import com.pcz.blog.domain.User;
import com.pcz.blog.domain.Vote;
import com.pcz.blog.service.BlogService;
import com.pcz.blog.service.CatalogService;
import com.pcz.blog.service.UserService;
import com.pcz.blog.util.ConstraintViolationExceptionHandler;
import com.pcz.blog.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.util.List;

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
    @Autowired
    private BlogService blogService;
    @Autowired
    private CatalogService catalogService;

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
                                @RequestParam(value = "catalogId", required = false) Long catalogId,
                                @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                @RequestParam(value = "async", required = false) boolean async,
                                @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);

        Page<Blog> blogPage = null;
        if (catalogId != null && catalogId > 0) {
            Catalog catalog = catalogService.getCatalogById(catalogId);
            Pageable pageable = new PageRequest(pageIndex, pageSize);
            blogPage = blogService.listBlogsByCatalog(catalog, pageable);
            order = "";
        } else if (order.equals("hot")) {
            Sort sort = new Sort(Sort.Direction.DESC, "readings", "comments", "likes");
            Pageable pageable = new PageRequest(pageIndex, pageSize, sort);
            blogPage = blogService.listBlogsByTitleLikeAndSort(user, keyword, pageable);
        } else if (order.equals("new")) {
            Pageable pageable = new PageRequest(pageIndex, pageSize);
            blogPage = blogService.listBlogsByTitleLike(user, keyword, pageable);
        }

        List<Blog> blogList = blogPage.getContent();
        model.addAttribute("order", order);
        model.addAttribute("page", blogPage);
        model.addAttribute("blogList", blogList);
        model.addAttribute("catalogId", catalogId);

        return async ? "/userspace/u :: #mainContainerReplace" : "/userspace/u";
    }

    @GetMapping("/{username}/blogs/{id}")
    public String listUserBlogById(@PathVariable("username") String username,
                                   @PathVariable("id") Long id, Model model) {
        Blog blog = blogService.getBlogById(id);

        blogService.readingIncrease(id);
        boolean isBlogOwner = false;
        User user = null;
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user != null && username.equals(user.getUsername())) {
                isBlogOwner = true;
            }
        }

        List<Vote> voteList = blog.getVoteList();
        Vote currentVote = null;
        if (user != null) {
            for (Vote vote : voteList) {
                if (vote.getUser().getUsername().equals(user.getUsername())) {
                    currentVote = vote;
                    break;
                }
            }
        }

        model.addAttribute("isBlogOwner", isBlogOwner);
        model.addAttribute("blogModel", blog);
        model.addAttribute("currentVote", currentVote);

        return "/userspace/blog";
    }

    @DeleteMapping("/{username}/blogs/{id}")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> deleteBlog(@PathVariable("username") String username,
                                               @PathVariable("id") Long id) {
        try {
            blogService.removeBlog(id);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "处理成功",
                "/u/" + username + "/blogs"));
    }

    @GetMapping("/{username}/blogs/edit")
    public ModelAndView createBlog(@PathVariable("username") String username,
                                   Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        List<Catalog> catalogs = catalogService.listCatalogs(user);

        model.addAttribute("blog", new Blog(null, null, null));
        model.addAttribute("catalogs", catalogs);

        return new ModelAndView("/userspace/blogedit", "blogModel", model);
    }

    @GetMapping("/{username}/blogs/edit/{id}")
    public ModelAndView editBlog(@PathVariable("username") String username,
                                 @PathVariable("id") Long id,
                                 Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        List<Catalog> catalogs = catalogService.listCatalogs(user);

        model.addAttribute("blog", blogService.getBlogById(id));
        model.addAttribute("catalogs", catalogs);

        return new ModelAndView("/userspace/blogedit", "blogModel", model);
    }

    @PostMapping("/{username}/blogs/edit")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> editBlog(@PathVariable("username") String username, @RequestBody Blog blog) {
        if (blog.getCatalog().getId() == null) {
            return ResponseEntity.ok().body(new Response(false, "未选择分类"));
        }

        User user = (User) userDetailsService.loadUserByUsername(username);
        blog.setUser(user);

        try {
            blog = blogService.saveBlog(blog);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "处理成功",
                "/u/" + username + "/blogs/" + blog.getId()));
    }
}
