package com.hanium.showerendorphins.domain;

import com.hanium.showerendorphins.enums.FeelingStatus;
import com.hanium.showerendorphins.enums.Note;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "aroma")
@Getter
public class Aroma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aroma_id")
    private Integer id;

    @Column(name = "ko_name")
    private String koName;

    @Column(name = "en_name")
    private String enName;

    @Column(name = "note")
    @Enumerated(EnumType.STRING)
    private Note note;

    @Column(name = "feeling")
    @Enumerated(EnumType.STRING)
    private FeelingStatus feeling;

    @Column(name = "scent")
    private String scent;

    @Column(name = "summary")
    private String summary;

    @Column(name = "caution")
    private String caution;

    @Column(name = "img_url")
    private String imgURL;

    @Builder
    public Aroma(Integer id, String koName, String enName, Note note, FeelingStatus feeling, String scent, String summary, String caution, String imgURL) {
        this.id = id;
        this.koName = koName;
        this.enName = enName;
        this.note = note;
        this.feeling = feeling;
        this.scent = scent;
        this.summary = summary;
        this.caution = caution;
        this.imgURL = imgURL;
    }


    public Aroma() {
    }

}
