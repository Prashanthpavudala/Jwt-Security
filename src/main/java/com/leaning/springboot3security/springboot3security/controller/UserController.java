package com.leaning.springboot3security.springboot3security.controller;

import com.leaning.springboot3security.springboot3security.aop.LogError;
import com.leaning.springboot3security.springboot3security.aop.Profile;
import com.leaning.springboot3security.springboot3security.dto.AuthRequest;
import com.leaning.springboot3security.springboot3security.dto.UserDTO;
import com.leaning.springboot3security.springboot3security.service.UserService;
import com.leaning.springboot3security.springboot3security.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Profile
    @LogError
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome, This endpoint is not secure";
    }

    @Profile
    @LogError
    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody UserDTO userDTO) {
        log.info("Inside addNewUser...");
        return userService.addUser(userDTO);
    }

    @Profile
    @LogError
    @PostMapping("/updateUser")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String updateUser(@RequestBody UserDTO userDTO) {
        log.info("Inside updateUser...");
        return userService.updateUser(userDTO);
    }

    @Profile
    @LogError
    @PostMapping("/deleteUser")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String deleteUser(@RequestParam String email) {
        log.info("Inside deleteUser...");
        return userService.deleteUser(email);
    }

    @Profile
    @LogError
    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public String userProfile() {
        return "Welcome to User Profile";
    }

    @Profile
    @LogError
    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @Profile
    @LogError
    @PostMapping("/generateToken")
    public String generateToken(@RequestBody AuthRequest authRequest) {
        log.info("Inside generateToken");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        if(authentication.isAuthenticated()) {
            return jwtUtils.generateToken(authRequest.getUserName());
        } else {
            throw new UsernameNotFoundException("Invalid User Request");
        }
    }


}
