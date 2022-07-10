package com.creativescrapyard.payload;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemResponse {

    private List<CreativeItemsDto> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPage;
    private Integer totalElements;

    private Boolean lastPage;

}
