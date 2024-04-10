package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
    private final ConcurrentHashMap<Long, Post> postMap = new ConcurrentHashMap<>();
    private final AtomicLong postIdSequence = new AtomicLong(1);

    public List<Post> all() {
        return Collections.list(postMap.elements());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(postMap.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            long id = postIdSequence.getAndIncrement();
            post.setId(id);
            postMap.put(id, post);
        } else {
            long id = post.getId();
            if (postMap.containsKey(id)) {
                postMap.put(id, post);
            } else {
                throw new NotFoundException("Post with id " + id + " not found for update");
            }
        }
        return post;
    }

    public void removeById(long id) {
        if (postMap.containsKey(id)) {
            postMap.remove(id);
        } else {
            throw new NotFoundException("Post with id " + id + " not found for delete");
        }
    }
}
