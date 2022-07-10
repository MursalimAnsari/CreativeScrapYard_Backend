package com.creativescrapyard.repo;

import com.creativescrapyard.model.CreativeCategory;
import com.creativescrapyard.payload.CreativeCategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreativeCategoryRepository extends JpaRepository<CreativeCategory, Long> {


}
