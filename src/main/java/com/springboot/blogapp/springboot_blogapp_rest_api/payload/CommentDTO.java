package com.springboot.blogapp.springboot_blogapp_rest_api.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CommentDTO {
    private long id;

    //name should not  be null or empty
    @NotEmpty(message = "Name should not be null or empty")
    private String name;

    //email should not be null or empty
    //email field validation
    @NotEmpty
    @Email(message = "Must be a well-formed email address")
    private String email;

    //comment body should ot be null or empty
    //comment body must be minimum 10 characters
    @NotEmpty
    @Size(min = 10, message = "Comment body must be minimum 10 characters")
    private String body;
}
