package com.creativescrapyard.payload;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
public class CreativeCategoryDto {

    private Long    creativeId;

    @NotEmpty(message = "field can't be null or blank")
    @Size(min = 5, max = 100, message = "category name must be in between 5 and 100 characters")
    private String  creativeCategoryName;

    @NotEmpty(message = "field can't be null or blank")
    @Size(min = 10, max = 1000, message = "category description must be in between 10 and 1000 characters")
    private String  creativeCategoryDescription;

}
