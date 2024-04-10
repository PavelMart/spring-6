package ru.netology.mappers;

import ru.netology.dtos.PostDto;
import ru.netology.model.Post;

public class PostMapper {
    public static PostDto mapToPostDto(Post post) {
        return new PostDto(post);
    }
}
