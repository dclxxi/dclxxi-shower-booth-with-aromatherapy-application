package com.hanium.showerendorphins.dto;

import com.hanium.showerendorphins.enums.FeelingStatus;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class AromaDetailDto {
    private FeelingStatus feeling;
    private String scent;
    private String summary;
    private String caution;
}
