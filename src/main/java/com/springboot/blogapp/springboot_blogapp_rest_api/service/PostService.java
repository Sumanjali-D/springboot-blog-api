package com.springboot.blogapp.springboot_blogapp_rest_api.service;

import com.springboot.blogapp.springboot_blogapp_rest_api.payload.PostDTO;
import com.springboot.blogapp.springboot_blogapp_rest_api.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDto);

     PostResponse getAllPosts(int pageNo, int pageSize, String sortBy,String sortDir);

    PostDTO getPostById(long id);

    PostDTO updatePost(PostDTO postDTO,long id);

    void deletePostById(long id);



}
