package ru.netology.service;

import org.springframework.stereotype.Service;
import ru.netology.dtos.PostDto;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.repository.PostRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<PostDto> all() {
        return repository.all();
    }

    public PostDto getById(long id) {
        return repository.getById(id).orElseThrow(NotFoundException::new);
    }

    public PostDto save(Post post) {
        return repository.save(post);
    }

    public void removeById(long id) {
        repository.removeById(id);
    }
}

