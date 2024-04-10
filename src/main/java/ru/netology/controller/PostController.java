package ru.netology.controller;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService service;
    final Gson gson = new Gson();

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public List<Post> all() throws IOException {
        return service.all();
    }

    @GetMapping("/{id}")
    public Post getById(@PathVariable long id) throws IOException {
        return service.getById(id);

    }

    @PostMapping
    public Post save(@RequestBody Post post) throws IOException {
        return service.save(post);
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable  long id) throws IOException {
        service.removeById(id);
    }
}
