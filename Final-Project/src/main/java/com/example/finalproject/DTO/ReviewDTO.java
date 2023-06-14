package com.example.finalproject.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewDTO {

    private Integer requestId;

    @NotEmpty(message = "Content is required")
    @Column(columnDefinition = "varchar(60)")
    private String content;

    @NotNull(message = "Rating is required")
    @Positive
    @Min(value = 1)
    @Max(value = 5)
    private Double rating;
}
