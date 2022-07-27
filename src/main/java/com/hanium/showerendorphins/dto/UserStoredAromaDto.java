package com.hanium.showerendorphins.dto;

import com.hanium.showerendorphins.domain.Aroma;
import com.hanium.showerendorphins.domain.User;
import com.hanium.showerendorphins.domain.UserStoredAroma;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class UserStoredAromaDto {
    private User user;
    private Aroma aroma;

    @Builder
    public UserStoredAromaDto(User user, Aroma aroma) {
        this.user = user;
        this.aroma = aroma;
    }

    public UserStoredAroma toEntity(){
        return UserStoredAroma.builder()
                .user(user)
                .aroma(aroma)
                .build();
    }
}
