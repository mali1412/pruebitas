package com.product.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.product.api.dto.out.DtoProductOut;
import com.product.api.entity.Product;

import jakarta.transaction.Transactional;

@Repository
public interface RepoProduct extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT p.product_id, p.gtin, p.product, p.description, p.price, p.stock, p.status, c.category " +
                   "FROM product p " +
                   "INNER JOIN category c ON c.category_id = p.category_id " +
                   "WHERE p.product_id = :product_id", nativeQuery = true)
    DtoProductOut getProduct(Integer product_id);

    @Query("SELECT p FROM Product p WHERE p.gtin = :gtin")
    Product findByGtin(@Param("gtin") String gtin);

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.stock = p.stock - :quantity WHERE p.gtin = :gtin")
    void decreaseStock(@Param("gtin") String gtin, @Param("quantity") Integer quantity);
}
