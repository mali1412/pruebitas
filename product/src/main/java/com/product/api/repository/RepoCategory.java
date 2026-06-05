package com.product.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.product.api.entity.Category;

import jakarta.transaction.Transactional;

@Repository
public interface RepoCategory extends JpaRepository<Category, Integer>{

    @Query(value ="SELECT * FROM category ORDER BY category", nativeQuery = true)
    List<Category> getCategories();
    
    List<Category> findByStatusOrderByCategoryAsc(Integer status);
   
    @Modifying
    @Transactional
    @Query(value = "UPDATE category SET status = :status WHERE category_id = :id", nativeQuery = true)
    void updateStatus(@Param("id") Integer id, @Param("status") Integer status);

}
