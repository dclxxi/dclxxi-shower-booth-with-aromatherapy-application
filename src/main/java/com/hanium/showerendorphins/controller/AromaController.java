package com.hanium.showerendorphins.controller;

import com.hanium.showerendorphins.domain.Aroma;
import com.hanium.showerendorphins.dto.AromaDetailDto;
import com.hanium.showerendorphins.dto.AromaListDto;
import com.hanium.showerendorphins.dto.UserStoredAromaDto;
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

    @GetMapping(value = "/Modify_User_Stored_AromaList")
    public void modifyUserStoredAromaListToJSON(@RequestBody List<UserStoredAromaDto> userStoredAromaDtoList) {
        userStoredAromaService.modifyUserStoredAroma(userStoredAromaDtoList);
    }

}
