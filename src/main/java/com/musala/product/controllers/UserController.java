package com.musala.product.controllers;

import com.musala.product.model.Role;
import com.musala.product.model.User;
import com.musala.product.repos.RoleRepo;
import com.musala.product.repos.UserRepo;
import com.musala.product.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {

    public final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    SecurityService securityService;
    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;


    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public String login(String email, String password) {
        boolean login = securityService.login(email, password);
        if (login) {
            return "index";
        } else return "login";
    }

    @GetMapping("/")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/showReg")
    public String showRegisterationPage() {
        return "registerUser";
    }

    @PostMapping("/registerUser")
    public String registerUser(User user,
                               @RequestParam(defaultValue = "false") boolean ADMIN,
                               @RequestParam(defaultValue = "false") boolean USER) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> user_roles = new HashSet<>();
        if (ADMIN) {
            user_roles.add(roleRepo.findByName("ROLE_ADMIN"));
        }
        if (USER) {
            user_roles.add(roleRepo.findByName("ROLE_USER"));
        }
        user.setRoles(user_roles);
        userRepo.save(user);
        return "login";
    }
}
