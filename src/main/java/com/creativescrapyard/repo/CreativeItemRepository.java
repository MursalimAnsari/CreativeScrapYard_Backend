package com.creativescrapyard.repo;

import com.creativescrapyard.model.CreativeCategory;
import com.creativescrapyard.model.CreativeItems;
import com.creativescrapyard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CreativeItemRepository extends JpaRepository<CreativeItems, Long> {


    List<CreativeItems> findByUser(User User);
    List<CreativeItems> findByCreativeCategory(CreativeCategory category);
    
    @Query("select p from CreativeItems p where p.creativeItemName like :key ")
    List<CreativeItems>searchByCreativeItemName(@Param("key") String creativeItemName);


}
