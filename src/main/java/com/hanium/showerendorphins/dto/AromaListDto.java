package com.hanium.showerendorphins.dto;

import com.hanium.showerendorphins.enums.Note;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class AromaListDto {
    private Integer id;
    private String koName;
    private String enName;
    private Note note;
    private String scent;
    private String imgURL;
}
