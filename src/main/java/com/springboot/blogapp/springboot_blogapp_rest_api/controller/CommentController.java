package com.springboot.blogapp.springboot_blogapp_rest_api.controller;


import com.springboot.blogapp.springboot_blogapp_rest_api.entity.Comment;
import com.springboot.blogapp.springboot_blogapp_rest_api.payload.CommentDTO;
import com.springboot.blogapp.springboot_blogapp_rest_api.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable(value = "postId") long postId,
                                                    @Valid  @RequestBody CommentDTO commentDTO){
        return new ResponseEntity<>(commentService.createComment(postId,commentDTO), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDTO> getCommentsByPostId(@PathVariable(value = "postId") long postId){
        return commentService.getCommentsByPostId(postId);
    }


    @GetMapping("/posts/{posId}/comments/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable(value = "posId") Long postId,
                                                     @PathVariable(value = "id") Long commentId){
        CommentDTO commentDTO = commentService.getCommentById(postId,commentId);
        return  new ResponseEntity<>(commentDTO,HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable(value = "postId") Long postId,
                                                    @PathVariable(value = "id") Long commentId,
                                                    @Valid @RequestBody CommentDTO commentDTO){
        CommentDTO upatedComment = commentService.updateComment(postId,commentId,commentDTO);
        return new ResponseEntity<>(upatedComment,HttpStatus.OK);
    }


    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId,
                                                @PathVariable(value = "id") Long commentId){
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("Comment Deleted Successfully",HttpStatus.OK);
    }
}
