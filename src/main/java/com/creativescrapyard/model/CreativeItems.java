package com.creativescrapyard.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "creative_items")
@Getter
@Setter
@NoArgsConstructor
public class CreativeItems {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long creativeItemId;


    private String creativeItemName;


    private String creativeItemDescription;


    private Double creativeItemPrice;


    private Date createdDate;


    private String creativeProductImages;


    private Long unitsInStock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private  CreativeCategory creativeCategory;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
