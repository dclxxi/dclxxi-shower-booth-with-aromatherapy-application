package com.hanium.showerendorphins.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanium.showerendorphins.enums.FeelingStatus;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "shower")
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Shower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shower_id")
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @CreatedDate
    @JoinColumn(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "height")
    private double height;

    @Column(name = "feeling")
    @Enumerated(EnumType.STRING)
    private FeelingStatus feeling;

    @Column(name = "body_temperature")
    private double bodyTemperature;

    @Column(name = "water_temperature")
    private double waterTemperature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aroma_id")
    private Aroma aroma;

    @Column(name = "rating")
    private double rating;

    @Builder
    public Shower(Integer id, User user, LocalDateTime createDate, double height, FeelingStatus feeling, double bodyTemperature, double waterTemperature, Aroma aroma, double rating) {
        this.id = id;
        this.user = user;
        this.createDate = createDate;
        this.height = height;
        this.feeling = feeling;
        this.bodyTemperature = bodyTemperature;
        this.waterTemperature = waterTemperature;
        this.aroma = aroma;
        this.rating = rating;
    }

    public Shower() {

    }
}
