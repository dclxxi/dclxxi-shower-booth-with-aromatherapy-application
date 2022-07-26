package com.hanium.showerendorphins.dto;

import com.hanium.showerendorphins.domain.Aroma;
import com.hanium.showerendorphins.enums.FeelingStatus;
import com.hanium.showerendorphins.enums.Note;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class AromaDto {
    private String koName;
    private String enName;
    private Note note;
    private FeelingStatus feeling;
    private String scent;
    private String summary;
    private String caution;
    private String imgURL;

    @Builder
    public AromaDto(String koName, String enName, Note note, FeelingStatus feeling, String scent, String summary, String caution, String imgURL) {
        this.koName = koName;
        this.enName = enName;
        this.note = note;
        this.feeling = feeling;
        this.scent = scent;
        this.summary = summary;
        this.caution = caution;
        this.imgURL = imgURL;
    }

    public Aroma toEntity() {
        return Aroma.builder()
                .koName(koName)
                .enName(enName)
                .note(note)
                .feeling(feeling)
                .scent(scent)
                .summary(summary)
                .caution(caution)
                .imgURL(imgURL)
                .build();
    }
}
