package com.creativescrapyard.controller;

import com.creativescrapyard.payload.ApiResponse;
import com.creativescrapyard.payload.CreativeCategoryDto;
import com.creativescrapyard.service.CreativeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/creative_categories")
public class CreativeCategoryController {

    // Autowired service

    @Autowired
    private CreativeCategoryService creativeCategoryService;


    //create
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<CreativeCategoryDto> makeCreativeCategory(@Valid @RequestBody CreativeCategoryDto creativeCategoryDto){

        CreativeCategoryDto createdCategory =    this.creativeCategoryService
                .createCategory(creativeCategoryDto);
        return new ResponseEntity<CreativeCategoryDto>(createdCategory, HttpStatus.CREATED);
    }

    //update
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{creativeId}")
    public ResponseEntity<CreativeCategoryDto>updateCreativeCategory( @Valid
     @RequestBody CreativeCategoryDto creativeCategoryDto, @PathVariable Long creativeId){

        CreativeCategoryDto updatedCategory =    this.creativeCategoryService
                .updateCategory(creativeCategoryDto, creativeId );
        return new ResponseEntity<CreativeCategoryDto>(updatedCategory, HttpStatus.OK);
    }

    //delete
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{creativeId}")
    public ResponseEntity<ApiResponse>deleteCreativeCategory(@PathVariable Long creativeId){
        this.creativeCategoryService.deleteCategory(creativeId);
        return new ResponseEntity<ApiResponse>
                (new ApiResponse("creative category is deleted successfully",true), HttpStatus.OK);
    }


    //get
    @PreAuthorize("hasAnyRole('ADMIN','NORMAL')")
    @GetMapping("/{creativeId}")
    public ResponseEntity<CreativeCategoryDto>getCreativeCategory(@PathVariable Long creativeId){

        CreativeCategoryDto category = this.creativeCategoryService.getSingleCreativeCategory(creativeId);

        return new ResponseEntity<CreativeCategoryDto>(category, HttpStatus.OK);
    }

    //getAll
    @PreAuthorize("hasAnyRole('ADMIN','NORMAL')")
    @GetMapping("/get_all")
    public ResponseEntity<List<CreativeCategoryDto>>getCreativeCategory(){

        List<CreativeCategoryDto> categories = this.creativeCategoryService.getAllCreativeCategories();

        return new ResponseEntity<List<CreativeCategoryDto>>(categories, HttpStatus.OK);
    }


}
