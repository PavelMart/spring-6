package ru.netology.controller;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

@Controller
public class PostController {
    private final PostService service;
    final Gson gson = new Gson();


    public PostController(PostService service) {
        this.service = service;
    }

    public void all(HttpServletResponse response) throws IOException {
        final var data = service.all();
        setResponseData(data, response);
    }

    public void getById(long id, HttpServletResponse response) throws IOException {
        try {
            final var data = service.getById(id);
            setResponseData(data, response);
        } catch (NotFoundException e) {
            setResponseData("Post with id " + id + " not found", response);
        }

    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        try {
            final var post = gson.fromJson(body, Post.class);
            final var data = service.save(post);
            setResponseData(data, response);
        } catch (IllegalArgumentException e) {
            setResponseData(e.getMessage(), response);
        }
    }

    public void removeById(long id, HttpServletResponse response) throws IOException {
        try {
            service.removeById(id);
            setResponseData("Post with id " + id + " has been deleted", response);
        } catch (NotFoundException e) {
            setResponseData("Post with id " + id + " not found", response);
        }
    }

    public void setResponseData(Object data, HttpServletResponse response) throws IOException {
        response.getWriter().print(gson.toJson(data));
    }
}
