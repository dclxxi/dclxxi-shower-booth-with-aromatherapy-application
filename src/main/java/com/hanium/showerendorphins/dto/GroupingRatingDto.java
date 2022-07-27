package com.hanium.showerendorphins.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupingRatingDto {
    private double rating;
    private long count;
}
