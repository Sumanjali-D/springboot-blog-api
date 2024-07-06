package com.springboot.blogapp.springboot_blogapp_rest_api.repository;

import com.springboot.blogapp.springboot_blogapp_rest_api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public  interface CategoryRepository extends JpaRepository<Category,Long> {

}
