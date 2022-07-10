package com.creativescrapyard.service;

import com.creativescrapyard.payload.CreativeItemsDto;
import com.creativescrapyard.payload.ItemResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CreativeItemService {

   //create
    CreativeItemsDto addCreativeItem(CreativeItemsDto creativeItemsDto, Long userId, Long creativeCategoryId);

    // update
    CreativeItemsDto updateCreativeItem(CreativeItemsDto creativeItemsDto, Long creativeItemId);

    //delete
    void deleteCreativeItem(Long creativeItemId);

    //get all Products of creative
    ItemResponse getAllCreativeItems(Integer pageNumber, Integer pageSize, String sortBy, String dir);

    // get product by id
    CreativeItemsDto getCreativeItemById(Long creativeItemId);

    //get all products of category
    ItemResponse getCreativeItemsByCategory(Integer pageNumber, Integer pageSize,Long creativeCategoryId);

    //search products by keyword
    List<CreativeItemsDto> searchCreativeItems(String keyword);


}
