package com.hanium.showerendorphins.controller;

import com.hanium.showerendorphins.service.ShowerService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ShowerHistory")
public class ShowerHistoryController {
    //샤워기록 관련 컨트롤러
    private final ShowerService showerService;

    @PostMapping(value = "/add_shower_log")
    public void addShowerLog(HttpServletRequest request) {
//        request.getParameter("userid")
//        showerService.createShowerLog()
    }
}
