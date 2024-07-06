package com.springboot.blogapp.springboot_blogapp_rest_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
//@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String email;
    private String body;


    //FetchType.LAZY tells hibernate to only fetch the related entities from database when you use the relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    //@JOinColumn annotation is used to specify foreign key
    private Post post;
}
