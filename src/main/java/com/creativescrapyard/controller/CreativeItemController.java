package com.creativescrapyard.controller;

import com.creativescrapyard.config.AppConstants;
import com.creativescrapyard.payload.ApiResponse;
import com.creativescrapyard.payload.CreativeItemsDto;
import com.creativescrapyard.payload.ItemResponse;
import com.creativescrapyard.service.CreativeItemService;
import com.creativescrapyard.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/creative/items")
public class CreativeItemController {

       @Autowired
       private CreativeItemService creativeItemService;

       @Autowired
       private FileService fileService;

       @Value("${project.image}")
       private String path;

 
    // create new creative item
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/user/{userId}/creativeCategory/{creativeCategoryId}/creativeItem")
    public ResponseEntity<CreativeItemsDto> newCreativeItem( @Valid @RequestBody CreativeItemsDto creativeItemsDto,
          @PathVariable Long  userId, @PathVariable Long creativeCategoryId ){

        CreativeItemsDto item = this.creativeItemService.addCreativeItem(creativeItemsDto,userId,creativeCategoryId);

        return  new ResponseEntity<CreativeItemsDto>(item, HttpStatus.CREATED);
    }

    // get all creative items by category
    @GetMapping("/category/{creativeCategoryId}/creativeItems")
    public ResponseEntity<ItemResponse>  getCreativeItemsByCategory(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10",required = false) Integer pageSize,
            @PathVariable Long creativeCategoryId){

         ItemResponse  items =
                 this.creativeItemService.getCreativeItemsByCategory(pageNumber,pageSize, creativeCategoryId);
         return new ResponseEntity<ItemResponse>(items, HttpStatus.OK);
    }


    // get all creative items
    @GetMapping("/getAllItems")
    public ResponseEntity<ItemResponse> getAllCreativeItems(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
            ){

        ItemResponse allItems=  this.creativeItemService.getAllCreativeItems(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<ItemResponse>(allItems, HttpStatus.OK);
    }

    // get creative item by id
    @GetMapping("/getItem/{creativeItemId}")
    public ResponseEntity<CreativeItemsDto> getCreativeItemById(@PathVariable Long creativeItemId){
       CreativeItemsDto itemsDto= this.creativeItemService.getCreativeItemById(creativeItemId);
       return  new ResponseEntity<CreativeItemsDto>(itemsDto, HttpStatus.OK);
    }

    // delete product by id
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/creativeItem/{creativeItemId}")
    public ApiResponse deleteItemById(@PathVariable Long creativeItemId){

        this.creativeItemService.deleteCreativeItem(creativeItemId);
        return new ApiResponse("Item deleted successfully ....", true);
    }

    // update
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/creativeItem/{creativeItemId}")
    public ResponseEntity<CreativeItemsDto>
    updateItem( @Valid @RequestBody CreativeItemsDto creativeItemsDto, @PathVariable Long creativeItemId) {
       CreativeItemsDto item= this.creativeItemService.updateCreativeItem(creativeItemsDto, creativeItemId);
        return new ResponseEntity<CreativeItemsDto>(item, HttpStatus.OK);
    }


    // search item by keyword
    @GetMapping("/creativeItem/search/{keyword}")
    public ResponseEntity<List<CreativeItemsDto>>  searchCreativeItemByName(
                  @PathVariable("keyword") String keyword) {

           List<CreativeItemsDto> allDtoList = this.creativeItemService.searchCreativeItems(keyword);
        return new ResponseEntity<List<CreativeItemsDto>>(allDtoList,HttpStatus.OK);
    }


    // image upload api
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/creativeItem/image/upload/{creativeItemId}")
    public  ResponseEntity<CreativeItemsDto> uploadCreativeItemImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable Long creativeItemId
             ) throws IOException {

        String fileName = this.fileService.uploadFile(path,image);

        CreativeItemsDto itemsDto   = this.creativeItemService.getCreativeItemById(creativeItemId);

        itemsDto.setCreativeProductImages(fileName);

        CreativeItemsDto updatedItem= this.creativeItemService.updateCreativeItem(itemsDto,creativeItemId);

        return new ResponseEntity<CreativeItemsDto>(updatedItem, HttpStatus.OK);
    }


    // download image

    @GetMapping(value = "creativeItem/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response ) throws IOException{
         InputStream resource =  this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }


}
