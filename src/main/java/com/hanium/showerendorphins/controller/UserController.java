package com.hanium.showerendorphins.controller;

import com.hanium.showerendorphins.domain.User;
import com.hanium.showerendorphins.dto.UserDto;
import com.hanium.showerendorphins.enums.Gender;
import com.hanium.showerendorphins.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/User")
public class UserController {
    //회원가입 및 로그인 관련
    private final UserService userService;

    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public String joinUser(UserDto joinUser, RedirectAttributes redirectAttributes) {
        userService.saveUser(joinUser);
        redirectAttributes.addAttribute("userId", joinUser.getUserId());
        return "success";
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.GET)
    public String updateUser(UserDto joinUser) {
        userService.saveUser(joinUser);
        return "success";
    }

    @RequestMapping(value = "/findUser")
    public User findUser(HttpServletRequest request) {
        String email = request.getParameter("email");
        return userService.signIn(email);
    }

    @RequestMapping(value = "/findUserCode")
    public Integer findUserCode(HttpServletRequest request) {
        String email = request.getParameter("email");
        return userService.signIn(email).getCode();
    }
}
