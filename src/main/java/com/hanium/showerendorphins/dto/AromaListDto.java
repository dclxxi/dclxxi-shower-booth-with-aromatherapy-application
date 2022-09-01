package com.hanium.showerendorphins.dto;

import com.hanium.showerendorphins.enums.Note;
import lombok.*;

import java.util.Objects;

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

    public AromaListDto(Integer id, String koName, String enName, String imgURL) {
        this.id = id;
        this.koName = koName;
        this.enName = enName;
        this.imgURL = imgURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AromaListDto that = (AromaListDto) o;
        return id.equals(that.id) && Objects.equals(koName, that.koName) && Objects.equals(enName, that.enName) && note == that.note && Objects.equals(scent, that.scent) && Objects.equals(imgURL, that.imgURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
