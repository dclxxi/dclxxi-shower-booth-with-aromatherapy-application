package com.hanium.showerendorphins.dto;

import lombok.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class UserStoredAromaListDto {
    private Integer aromaId;
    private String koName;
    private String enName;
    private String imgURL;
    private Boolean isChecked;

    public UserStoredAromaListDto(Integer aromaId, String koName, String enName, String imgURL) {
        this.aromaId = aromaId;
        this.koName = koName;
        this.enName = enName;
        this.imgURL = imgURL;
    }
}
