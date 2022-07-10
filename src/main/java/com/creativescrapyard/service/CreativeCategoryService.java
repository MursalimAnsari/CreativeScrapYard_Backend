package com.creativescrapyard.service;

import com.creativescrapyard.payload.CreativeCategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CreativeCategoryService {

    //create
    CreativeCategoryDto createCategory(CreativeCategoryDto creativeCategoryDto);

    //delete
    CreativeCategoryDto updateCategory(CreativeCategoryDto creativeCategoryDto, Long creativeId);

    //update
    public  void deleteCategory(Long creativeId);

    //get
    CreativeCategoryDto getSingleCreativeCategory(Long creativeId);

    //getAll

    List<CreativeCategoryDto> getAllCreativeCategories();

}
