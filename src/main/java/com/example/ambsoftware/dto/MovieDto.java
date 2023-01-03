package com.example.ambsoftware.dto;

import com.example.ambsoftware.domain.Rating;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDto {

    private int releaseYear;
    private String category;
    private String description;
    private List<Rating> ratings;
}
