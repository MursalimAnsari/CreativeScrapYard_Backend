package com.creativescrapyard.service.impl;

import com.creativescrapyard.exceptions.ResourceNotFoundException;
import com.creativescrapyard.model.CreativeCategory;
import com.creativescrapyard.model.CreativeItems;
import com.creativescrapyard.model.User;
import com.creativescrapyard.payload.CreativeItemsDto;
import com.creativescrapyard.payload.ItemResponse;
import com.creativescrapyard.repo.CreativeCategoryRepository;
import com.creativescrapyard.repo.CreativeItemRepository;
import com.creativescrapyard.repo.UserRepository;
import com.creativescrapyard.service.CreativeItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreativeItemServiceImpl implements CreativeItemService {

    @Autowired
    private CreativeItemRepository creativeItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreativeCategoryRepository creativeCategoryRepository;


    // create new item...
    @Override
    public CreativeItemsDto addCreativeItem(CreativeItemsDto creativeItemsDto, Long userId, Long creativeCategoryId) {

        CreativeItems creativeItem1 =  this.modelMapper.map(creativeItemsDto, CreativeItems.class);

        User user = this.userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("user","id", userId));

        CreativeCategory cat = this.creativeCategoryRepository.findById(creativeCategoryId)
                .orElseThrow(()-> new ResourceNotFoundException("creative category","id", creativeCategoryId));

        creativeItem1.setCreativeProductImages("default.png");
        creativeItem1.setCreatedDate(new Date());
        creativeItem1.setUser(user);
        creativeItem1.setCreativeCategory(cat);

        CreativeItems updatedItem = this.creativeItemRepository.save(creativeItem1);

        return this.modelMapper.map(updatedItem, CreativeItemsDto.class);
    }


    // update new item...
    @Override
    public CreativeItemsDto updateCreativeItem(CreativeItemsDto creativeItemsDto, Long creativeItemId) {

        CreativeItems item = this.creativeItemRepository.findById(creativeItemId)
                     .orElseThrow(()-> new ResourceNotFoundException("creative item ","id",creativeItemId));

             item.setCreativeItemName(creativeItemsDto.getCreativeItemName());
             item.setCreativeItemDescription(creativeItemsDto.getCreativeItemDescription());
             item.setCreativeItemPrice(creativeItemsDto.getCreativeItemPrice());
             item.setUnitsInStock(creativeItemsDto.getUnitsInStock());
             item.setCreativeProductImages(creativeItemsDto.getCreativeProductImages());

              CreativeItems updatedItem= this.creativeItemRepository.save(item);

             return this.modelMapper.map(updatedItem, CreativeItemsDto.class);
    }

    @Override
    public void deleteCreativeItem(Long creativeItemId) {

        CreativeItems item = this.creativeItemRepository.findById(creativeItemId)
                .orElseThrow(()-> new ResourceNotFoundException("creative item ","id", creativeItemId));

         this.creativeItemRepository.delete(item);
    }

    @Override
    public ItemResponse getAllCreativeItems(Integer pageNumber, Integer pageSize,String sortBy, String sortDir) {


        Sort sort= null;

        if(sortDir.equalsIgnoreCase("asc")){

            sort = Sort.by(sortBy).ascending();
        } else{
            sort = Sort.by(sortBy).descending();
        }

        Pageable p=  PageRequest.of(pageNumber, pageSize,sort);

        Page<CreativeItems> pageItems= this.creativeItemRepository.findAll(p);

        List<CreativeItems> allItems = pageItems.getContent();

       List<CreativeItemsDto> allItemsDto= allItems.stream().map((item)-> this.modelMapper
                .map(item,CreativeItemsDto.class)).collect(Collectors.toList());

       ItemResponse itemResponse = new ItemResponse();

       itemResponse.setContent(allItemsDto);
       itemResponse.setPageNumber(pageItems.getNumber());
       itemResponse.setPageSize(pageItems.getSize());
       itemResponse.setTotalPage(pageItems.getTotalPages());
       itemResponse.setTotalElements(pageItems.getNumberOfElements());
       itemResponse.setLastPage(pageItems.isLast());

        return itemResponse;
    }

    @Override
    public CreativeItemsDto getCreativeItemById(Long creativeItemId) {

        CreativeItems item = this.creativeItemRepository.findById(creativeItemId)
                .orElseThrow(()-> new ResourceNotFoundException("creative item","id",creativeItemId));

        return this.modelMapper.map(item, CreativeItemsDto.class);
    }

    @Override
    public ItemResponse getCreativeItemsByCategory(Integer pageNumber,Integer pageSize,Long creativeCategoryId) {

        CreativeCategory cat = this.creativeCategoryRepository.findById(creativeCategoryId).
                orElseThrow(()-> new ResourceNotFoundException("category","id", creativeCategoryId));


        List<CreativeItems> items=   this.creativeItemRepository.findByCreativeCategory(cat);


       List<CreativeItemsDto> allItems= items.stream().map((item)->this.modelMapper
                       .map(item, CreativeItemsDto.class)).collect(Collectors.toList());

        Pageable p=  PageRequest.of(pageNumber, pageSize);

        Page<CreativeItems> pageItems= this.creativeItemRepository.findAll(p);


        ItemResponse itemResponse = new ItemResponse();

        itemResponse.setContent(allItems);
        itemResponse.setPageNumber(pageItems.getNumber());
        itemResponse.setPageSize(pageItems.getSize());
        itemResponse.setTotalPage(pageItems.getTotalPages());
        itemResponse.setTotalElements(pageItems.getNumberOfElements());
        itemResponse.setLastPage(pageItems.isLast());

        return itemResponse;
    }

    // TODO : implement get all creative products by a user

    @Override
    public List<CreativeItemsDto> searchCreativeItems(String keyword) {

         List<CreativeItems> items =this.creativeItemRepository.searchByCreativeItemName("%"+keyword+"%");
        List<CreativeItemsDto> itemDtoList = items.stream().map((item)->this.modelMapper
                                  .map(item, CreativeItemsDto.class)).collect(Collectors.toList());
        return itemDtoList;
    }

}