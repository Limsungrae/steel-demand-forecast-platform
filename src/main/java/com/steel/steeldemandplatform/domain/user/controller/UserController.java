package com.steel.steeldemandplatform.domain.user.controller;

import com.steel.steeldemandplatform.domain.user.dto.SignupRequest;
import com.steel.steeldemandplatform.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import com.steel.steeldemandplatform.domain.user.dto.LoginRequest;
import com.steel.steeldemandplatform.domain.user.entity.User;
/*
 * 회원 API
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /*
     * 회원가입
     */
    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest request) {

        userService.signup(request);

        return "회원가입 성공";
    }

    @PostMapping("/login")
    public String login(
            @RequestBody LoginRequest request,
            HttpSession session
    ) {

        User loginUser = userService.login(request);

        session.setAttribute(
                "loginUser",
                loginUser
        );

        return "로그인 성공";
    }
}