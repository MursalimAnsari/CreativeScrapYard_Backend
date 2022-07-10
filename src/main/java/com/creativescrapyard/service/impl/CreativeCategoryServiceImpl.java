package com.creativescrapyard.service.impl;

import com.creativescrapyard.exceptions.ResourceNotFoundException;
import com.creativescrapyard.model.CreativeCategory;
import com.creativescrapyard.payload.CreativeCategoryDto;
import com.creativescrapyard.repo.CreativeCategoryRepository;
import com.creativescrapyard.service.CreativeCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreativeCategoryServiceImpl implements CreativeCategoryService {

    @Autowired
    private CreativeCategoryRepository creativeCategoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    // create new category
    @Override
    public CreativeCategoryDto createCategory(CreativeCategoryDto creativeCategoryDto) {

       CreativeCategory cat =this.modelMapper.map(creativeCategoryDto, CreativeCategory.class);
        CreativeCategory addedCategory=this.creativeCategoryRepository.save(cat);

        return this.modelMapper.map(addedCategory, CreativeCategoryDto.class);
    }


    //update the old category
    @Override
    public CreativeCategoryDto updateCategory(CreativeCategoryDto creativeCategoryDto, Long creativeId) {

        CreativeCategory category = creativeCategoryRepository.findById(creativeId).
                orElseThrow(()-> new ResourceNotFoundException("Creative Category","id",creativeId));

        category.setCreativeCategoryName(creativeCategoryDto.getCreativeCategoryName());
        category.setCreativeCategoryDescription(creativeCategoryDto.getCreativeCategoryDescription());

        CreativeCategory updateCategory = this.creativeCategoryRepository.save(category);

        return this.modelMapper.map(updateCategory, CreativeCategoryDto.class);
    }


    // delete category by id
    @Override
    public void deleteCategory(Long creativeId) {

         CreativeCategory category= this.creativeCategoryRepository.findById(creativeId)
                 .orElseThrow(()-> new ResourceNotFoundException("Creative Category","id", creativeId));

         this.creativeCategoryRepository.delete(category);
    }

    // get single category
    @Override
    public CreativeCategoryDto getSingleCreativeCategory(Long creativeId) {

        CreativeCategory category = this.creativeCategoryRepository.findById(creativeId)
                .orElseThrow(()-> new ResourceNotFoundException("creative category","id",creativeId));

        return this.modelMapper.map(category, CreativeCategoryDto.class);
    }


    // get all categories
    @Override
    public List<CreativeCategoryDto> getAllCreativeCategories() {

         List<CreativeCategory> categories = this.creativeCategoryRepository.findAll();

        List<CreativeCategoryDto> categoryDtos= categories.stream().map((cat)-> this.modelMapper
                                           .map(cat, CreativeCategoryDto.class)).collect(Collectors.toList());
        return categoryDtos;
    }
}
