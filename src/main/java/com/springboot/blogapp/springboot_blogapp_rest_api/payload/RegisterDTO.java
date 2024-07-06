package com.springboot.blogapp.springboot_blogapp_rest_api.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    private String name;
    private String username;
    private String email;
    private String password;

}
