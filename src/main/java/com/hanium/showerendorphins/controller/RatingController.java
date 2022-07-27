package com.hanium.showerendorphins.controller;

import com.hanium.showerendorphins.dto.GroupingRatingDto;
import com.hanium.showerendorphins.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Rating")
public class RatingController {

    private final RatingService ratingService;

    @GetMapping(value = "/All_Rating_List")
    public List<GroupingRatingDto> allRatingListToJSON() {
        return ratingService.allRatingList();
    }

    @GetMapping(value = "/User_Rating_List")
    public List<GroupingRatingDto> userRatingListToJSON() {
        return ratingService.allRatingList();
    }
}
