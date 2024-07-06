package com.springboot.blogapp.springboot_blogapp_rest_api.repository;

import com.springboot.blogapp.springboot_blogapp_rest_api.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
