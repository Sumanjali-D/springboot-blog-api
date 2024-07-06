package com.springboot.blogapp.springboot_blogapp_rest_api.payload;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDTO {
    private long id;

    //title should not be null or empty
    // title should have atleast 2 characters
    @NotEmpty
    @Size(min = 2, message = "Post Title should have at least 2 characters")
    private String title;

    //description should not be null or empty
    // description should have atleast 10 characters
    @NotEmpty
    @Size(min = 10, message = "Post Description should have at least 2 characters")
    private String description;

    //description should not be null or empty
    @NotEmpty
    private String content;
    private Set<CommentDTO> comments;

    private Long categoryId;

}
