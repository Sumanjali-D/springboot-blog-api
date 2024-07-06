package com.springboot.blogapp.springboot_blogapp_rest_api.service.impl;

import com.springboot.blogapp.springboot_blogapp_rest_api.entity.Comment;
import com.springboot.blogapp.springboot_blogapp_rest_api.entity.Post;
import com.springboot.blogapp.springboot_blogapp_rest_api.exception.BlogAPIException;
import com.springboot.blogapp.springboot_blogapp_rest_api.exception.ResourceNotFoundException;
import com.springboot.blogapp.springboot_blogapp_rest_api.payload.CommentDTO;
import com.springboot.blogapp.springboot_blogapp_rest_api.repository.CommentRepository;
import com.springboot.blogapp.springboot_blogapp_rest_api.repository.PostRepository;
import com.springboot.blogapp.springboot_blogapp_rest_api.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository,ModelMapper mapper){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }
    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {
        Comment comment = mapToEntity(commentDTO);

        //retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        //set post to comment entity
        comment.setPost(post);
        //save comment entity to the database
        Comment newComment = commentRepository.save(comment);
        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(long postId) {
        //retrieve comments by post id
        List<Comment> comments = commentRepository.findByPostId(postId);

        //convert list of comment entities to list of comment dto's
        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(Long postId, Long commentId) {
        //retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));

        //retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id",commentId));
       if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
       }
        return mapToDTO(comment);
    }

    @Override
    public CommentDTO updateComment(Long postId, long commentId, CommentDTO commentRequest) {
        //retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));

        //retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id",commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }

        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());
        Comment updatedComment = commentRepository.save(comment);

        return mapToDTO(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        //retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));

        //retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id",commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }

        commentRepository.delete(comment);
    }


    private CommentDTO mapToDTO(Comment comment){
        CommentDTO commentDTO = mapper.map(comment,CommentDTO.class);
//        CommentDTO commentDTO = new CommentDTO();
//        commentDTO.setId(comment.getId());
//        commentDTO.setName(comment.getName());
//        commentDTO.setBody(comment.getBody());
//        commentDTO.setEmail(comment.getEmail());
        return commentDTO;
    }


    private Comment mapToEntity(CommentDTO commentDTO){
        Comment comment = mapper.map(commentDTO,Comment.class);
//        Comment comment = new Comment();
//        comment.setId(commentDTO.getId());
//        comment.setName(commentDTO.getName());
//        comment.setEmail(commentDTO.getEmail());
//        comment.setBody(commentDTO.getBody());
        return  comment;
    }
}
