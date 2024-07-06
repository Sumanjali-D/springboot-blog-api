package com.springboot.blogapp.springboot_blogapp_rest_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

//JPA Entity
//we used lombok annotation to get getters ,setters and constructors at runtime

//@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


@Entity
@Table(
        name = "posts" , uniqueConstraints = {@UniqueConstraint(columnNames ={"title"} )}
)
public class Post {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
   @Column(name = "title",nullable = false)
    private String title;
    @Column(name = "description",nullable = false)
    private String description;
    @Column(name = "content",nullable = false)
    private String content;

    //because it's a bidirectional mapping we use one-to-many mapping here
    //CascadeType.ALL says that whenever the parent gets saved the child automatically gets saved
    //orphanRemoval = true says that whenever we remove parent child will automatically get's deleted
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    //One catagory  have multiple post and multiple posts belong to one category

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

}
