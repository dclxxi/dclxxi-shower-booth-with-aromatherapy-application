package com.hanium.showerendorphins.controller;

import com.hanium.showerendorphins.domain.Aroma;
import com.hanium.showerendorphins.domain.Shower;
import com.hanium.showerendorphins.dto.GroupingRatingDto;
import com.hanium.showerendorphins.dto.ShowerDto;
import com.hanium.showerendorphins.dto.UserDto;
import com.hanium.showerendorphins.service.ShowerService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ShowerHistory")
public class ShowerHistoryController {
    //샤워기록 관련 컨트롤러
    private final ShowerService showerService;

    @RequestMapping(value = "/add_shower_log")
    public Integer addShowerLog(@ModelAttribute ShowerDto showerDto, @RequestParam(value="userid") Integer userid) {
        return showerService.createShowerLog(showerDto, userid);
    }
    @GetMapping(value = "/shower_log_list")
    public List<Shower> showerLogListToJSON(@RequestParam(value="usercode") Integer usercode) {
        return showerService.findShowerLogList(usercode);
    }
    @GetMapping(value = "/user_rating_count")
    public List<GroupingRatingDto> userRatingCountToJSON(@RequestParam(value="usercode") Integer usercode) {
        return showerService.userRatingCount(usercode);
    }
    @GetMapping(value = "/all_rating_count")
    public List<GroupingRatingDto> allRatingCountToJSON() {
        return showerService.allRatingCount();
    }
}
