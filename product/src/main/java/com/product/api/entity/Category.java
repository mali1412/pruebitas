package com.product.api.entity;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class Category {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    @JsonProperty("id")
    private Integer category_id;
    
    @Column(name = "tag")
    @JsonProperty("tag")
    private String tag;

    @Column(name = "category")
    @JsonProperty("category")
    private String category;
    
    @Column(name = "status")
    @JsonProperty("status")
    private Integer status;

    public Category() {}

    public Category(Integer category_id, String category, String tag, Integer status) {
        this.category_id = category_id;
        this.category = category;
        this.tag = tag;
        this.status = status;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public String getCategory() {
        return category;
    }

    public String getTag() {
        return tag;
    }   

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        if (status == 0 || status == 1) {
            this.status = status;
        } else {
            System.out.println("El estatus debe 0 or 1");
        }
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }
    
    @Override
    public String toString() {
        return String.format("{%d,%s,%s,%d}", category_id, category, tag, status);
    }
}