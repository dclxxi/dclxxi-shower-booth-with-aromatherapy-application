package com.hanium.showerendorphins.dto;

import com.hanium.showerendorphins.domain.Aroma;
import com.hanium.showerendorphins.enums.FeelingStatus;
import com.hanium.showerendorphins.domain.Shower;
import com.hanium.showerendorphins.domain.User;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class ShowerDto {
    private User user;
    private double height;
    private FeelingStatus feeling;
    private double bodyTemperature;
    private Aroma aroma;
    private double rating;

    @Builder
    public ShowerDto(User user, double height, FeelingStatus feeling, double bodyTemperature, Aroma aroma,double rating) {
        this.user = user;
        this.height = height;
        this.feeling = feeling;
        this.bodyTemperature = bodyTemperature;
        this.aroma = aroma;
        this.rating = rating;
    }

    public Shower toEntity() {
        return Shower.builder()
                .user(user)
                .height(height)
                .feeling(feeling)
                .bodyTemperature(bodyTemperature)
                .aroma(aroma)
                .rating(rating)
                .build();
    }
}
