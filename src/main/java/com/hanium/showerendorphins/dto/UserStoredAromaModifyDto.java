package com.hanium.showerendorphins.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class UserStoredAromaModifyDto {
    private String userId;
    private List<Integer> aromaId;
}
