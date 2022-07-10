package com.creativescrapyard.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class CreativeCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    creativeId;


    private String  creativeCategoryName;


    private String  creativeCategoryDescription;


    @OneToMany(mappedBy = "creativeCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<CreativeItems> creativeItems= new ArrayList<>();

}
