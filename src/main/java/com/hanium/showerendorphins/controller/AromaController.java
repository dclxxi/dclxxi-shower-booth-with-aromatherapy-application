package com.hanium.showerendorphins.controller;

import com.hanium.showerendorphins.domain.Aroma;
import com.hanium.showerendorphins.dto.GroupingRatingDto;
import com.hanium.showerendorphins.service.AromaService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Aroma")
public class AromaController {

    private final AromaService aromaService;

    @GetMapping(value = "/All_Aroma_List")
    public List<Aroma> allAromaListToJSON() {
        return aromaService.findAllAromaList();
    }
}
