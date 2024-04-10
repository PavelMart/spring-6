package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.dtos.PostDto;
import ru.netology.exception.NotFoundException;
import ru.netology.mappers.PostMapper;
import ru.netology.model.Post;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class PostRepository {
    private final ConcurrentHashMap<Long, Post> postMap = new ConcurrentHashMap<>();
    private final AtomicLong postIdSequence = new AtomicLong(1);

    public List<PostDto> all() {

        return postMap.entrySet()
                        .stream()
                        .filter(entry -> !entry.getValue().isRemoved())
                        .map(entry -> PostMapper.mapToPostDto(entry.getValue()))
                        .collect(Collectors.toList());
    }

    public Optional<PostDto> getById(long id) {
        final var post = postMap.get(id);
        if (post == null || post.isRemoved()) {
            throw new NotFoundException();
        }
        return Optional.of(PostMapper.mapToPostDto(post));
    }

    public PostDto save(Post post) {
        if (post.getId() == 0) {
            long id = postIdSequence.getAndIncrement();
            post.setId(id);
            postMap.put(id, post);
        } else {
            long id = post.getId();
            final var oldPost = postMap.get(id);
            if (oldPost == null || oldPost.isRemoved()) {
                throw new NotFoundException();
            }
            oldPost.setContent(post.getContent());
        }
        return PostMapper.mapToPostDto(post);
    }

    public void removeById(long id) {
        final var post = postMap.get(id);
        if (post == null) {
            throw new NotFoundException();
        }
        post.setRemoved(true);
    }
}
