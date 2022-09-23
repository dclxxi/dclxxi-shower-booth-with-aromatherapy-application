package com.hanium.showerendorphins.dto;

import com.hanium.showerendorphins.enums.FeelingStatus;
import lombok.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class UserStoredAromaSelectionDto {
    private Integer aromaId;
    private String koName;
    private FeelingStatus feeling;
    private String imgURL;
}
