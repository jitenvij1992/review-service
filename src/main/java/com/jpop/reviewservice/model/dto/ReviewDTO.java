package com.jpop.reviewservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class ReviewDTO {

    @NotNull(message = "Description can't be null")
    @Size(message = "Description should be minimum 5 letter and maximum 200 letters", min = 5, max = 200)
    private String description;

    @NotNull(message = "Rating can't be null")
    private long rating;
}
