package ru.netology.dtos;

import ru.netology.model.Post;

public class PostDto {
    private long id;
    private String content;

    public PostDto(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
    }
}
