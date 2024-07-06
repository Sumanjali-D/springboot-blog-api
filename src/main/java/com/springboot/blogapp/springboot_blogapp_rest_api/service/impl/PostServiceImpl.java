package com.springboot.blogapp.springboot_blogapp_rest_api.service.impl;

import com.springboot.blogapp.springboot_blogapp_rest_api.entity.Category;
import com.springboot.blogapp.springboot_blogapp_rest_api.entity.Post;
import com.springboot.blogapp.springboot_blogapp_rest_api.exception.ResourceNotFoundException;
import com.springboot.blogapp.springboot_blogapp_rest_api.payload.CategoryDTO;
import com.springboot.blogapp.springboot_blogapp_rest_api.payload.PostDTO;
import com.springboot.blogapp.springboot_blogapp_rest_api.payload.PostResponse;
import com.springboot.blogapp.springboot_blogapp_rest_api.repository.CategoryRepository;
import com.springboot.blogapp.springboot_blogapp_rest_api.repository.PostRepository;
import com.springboot.blogapp.springboot_blogapp_rest_api.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper mapper;

    private CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDTO createPost(PostDTO postDto) {

        //convert DTO to entity
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());

        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(()->new ResourceNotFoundException("Category","id",postDto.getCategoryId()));
        Post post = mapToEntity(postDto);
        post.setCategory(category);
        Post newPost = postRepository.save(post);

        //Convert Entity to DTO
//        PostDTO postResponse = new PostDTO();
//        postResponse.setId(newPost.getId());
//        postResponse.setTitle(newPost.getTitle());
//        postResponse.setDescription(newPost.getDescription());
//        postResponse.setContent(newPost.getContent());
        PostDTO postResponse = mapToDTO(newPost);
        return postResponse;
    }


    //get all posts rest api
    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        //create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        //sort.by(sortBy).descending()

        Page<Post> posts = postRepository.findAll(pageable);

        //get content from page object
        List<Post> listOfPosts = posts.getContent();
        List<PostDTO> content = listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;

    }

    @Override
    public PostDTO getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, long id) {
        //get post by id from the database and if the post is not available in the database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        Post updatePost = postRepository.save(post);
        return mapToDTO(updatePost);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }


    //Converted Entity into DTO
    private PostDTO mapToDTO(Post post) {
        PostDTO postDTO = mapper.map(post, PostDTO.class);
//        PostDTO postDTO = new PostDTO();
//        postDTO.setId(post.getId());
//        postDTO.setTitle(post.getTitle());
//        postDTO.setDescription(post.getDescription());
//        postDTO.setContent(post.getContent());

        //we use ModelMapper methods to map the above

        return postDTO;
    }


    //convert DTO to entity
    private Post mapToEntity(PostDTO postDTO) {
        Post post = mapper.map(postDTO, Post.class);
//        Post post = new Post();
//        post.setTitle(postDTO.getTitle());
//        post.setDescription(postDTO.getDescription());
//        post.setContent(postDTO.getContent());
        return post;
    }


}
