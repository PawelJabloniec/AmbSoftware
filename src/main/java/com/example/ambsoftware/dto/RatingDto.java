package com.example.ambsoftware.dto;

import com.example.ambsoftware.domain.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingDto {
    private int rate;
    private Movie movie;
}
