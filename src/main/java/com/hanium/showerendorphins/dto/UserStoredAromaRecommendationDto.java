package com.hanium.showerendorphins.dto;

import lombok.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class UserStoredAromaRecommendationDto {
    private Integer aromaId;
    private String koName;
    private String imgURL;
}
