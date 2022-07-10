package com.creativescrapyard.payload;

import com.creativescrapyard.model.CreativeCategory;
import com.creativescrapyard.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
public class CreativeItemsDto {

    private  Long creativeItemId;

    @NotEmpty(message = "field can't be null or empty")
    @Size(min = 8, max = 50, message = "name must be in between length 8 and 50 characters")
    private String creativeItemName;

    @NotEmpty(message = "field can't be null or empty")
    @Size(min = 10, max = 1000, message = "name must be in between length 10 and 1000 characters")
    private String creativeItemDescription;


    @NotNull(message = "field can't be null")
    private Double creativeItemPrice;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @NotEmpty(message = "field can't be null or empty")
    @Size(min =3, max = 2000, message = "name must be in between length 8 and 2000 characters")
    private String creativeProductImages;

    @NotNull(message = "field can't be null")
    private Long unitsInStock;


    private CreativeCategoryDto creativeCategory;

    private UserDto user;


}
