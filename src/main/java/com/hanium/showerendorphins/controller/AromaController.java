package com.hanium.showerendorphins.controller;

import com.hanium.showerendorphins.domain.Aroma;
import com.hanium.showerendorphins.domain.UserStoredAroma;
import com.hanium.showerendorphins.dto.UserStoredAromaDto;
import com.hanium.showerendorphins.service.AromaService;
import com.hanium.showerendorphins.service.UserStoredAromaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Aroma")
public class AromaController {

    private final AromaService aromaService;
    private final UserStoredAromaService userStoredAromaService;

    @GetMapping(value = "/All_Aroma_List")
    public List<Aroma> allAromaListToJSON() {
        return aromaService.findAllAromaList();
    }

    @GetMapping(value = "/All_User_Stored_Aroma_List")
    public List<Aroma> allUserStoredAromaListToJSON(@RequestParam String userId) {
        return userStoredAromaService.findUserStoredAromaListByUserId(userId);
    }

    @GetMapping(value = "/Modify_User_Stored_AromaList")
    public void modifyUserStoredAromaListToJSON(@RequestBody List<UserStoredAromaDto> userStoredAromaDtoList) {
        userStoredAromaService.modifyUserStoredAroma(userStoredAromaDtoList);
    }
}
