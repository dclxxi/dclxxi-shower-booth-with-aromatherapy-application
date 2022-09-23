package com.hanium.showerendorphins.controller;

import com.hanium.showerendorphins.dto.*;
import com.hanium.showerendorphins.repository.UserStoredAromaRepository;
import com.hanium.showerendorphins.service.AromaService;
import com.hanium.showerendorphins.service.UserStoredAromaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Aroma")
public class AromaController {

    private final AromaService aromaService;
    private final UserStoredAromaService userStoredAromaService;

    private final UserStoredAromaRepository userStoredAromaRepository;

    @GetMapping(value = "/All_Aroma_List")
    public List<AromaListDto> allAromaListToJSON() {
        return aromaService.findAllAromaList();
    }

    @GetMapping(value = "/All_User_Stored_Aroma_List")
    public List<AromaListDto> allUserStoredAromaListToJSON(@RequestParam String userId) {
        return userStoredAromaService.findUserStoredAromaListByUserId(userId);
    }

    @GetMapping(value = "/Aroma_Info_Detail")
    public Optional<AromaDetailDto> aromaInfoDetailToJSON(@RequestParam Integer id) {
        return aromaService.findInfoDetailById(id);
    }

    @PostMapping(value = "/Modify_User_Stored_AromaList")
    public void modifyUserStoredAromaListToJSON(@RequestBody UserStoredAromaModifyDto userStoredAromaModifyDto) {
        userStoredAromaService.modifyUserStoredAroma(userStoredAromaModifyDto);
    }

    @GetMapping(value = "/All_User_Stored_Aroma_Modify_List")
    public List<UserStoredAromaListDto> allUserStoredAromaToJSON(@RequestParam String userId) {
        return userStoredAromaService.findAllUserStoredAromaList(userId);
    }

    @GetMapping(value = "/User_Stored_Aroma_Recommendation")
    public Optional<UserStoredAromaRecommendationDto> findUserStoredAromaIdByUserIdAndFeelingToJSON(@RequestParam String userId, @RequestParam String feeling) {
        return userStoredAromaService.findUserStoredAromaIdByUserIdAndFeeling(userId, feeling);
    }

}
