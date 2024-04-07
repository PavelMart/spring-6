package ru.netology.router;

import ru.netology.controller.PostController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Router {
    private final PostController controller;
    private final Map<String, HashMap<Pattern, RouteHandler>> handlers = new HashMap<>();

    public Router(PostController controller) {
        this.controller = controller;

        handlers.put("GET",
                new HashMap<>() {
                    {
                        put(Pattern.compile("/api/posts"), (HttpServletRequest req, HttpServletResponse resp) -> {
                            controller.all(resp);
                        });
                        put(Pattern.compile("/api/posts/(\\d+)"), (HttpServletRequest req, HttpServletResponse resp) -> {
                            final var path = req.getRequestURI();
                            final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                            controller.getById(id, resp);
                        });
                    }
                });
        handlers.put("POST",
                new HashMap<>() {
                    {
                        put(Pattern.compile("/api/posts"), (HttpServletRequest req, HttpServletResponse resp) -> {
                            controller.save(req.getReader(), resp);
                        });
                    }
                });
        handlers.put("DELETE",
                new HashMap<>() {
                    {
                        put(Pattern.compile("/api/posts/(\\d+)"), (HttpServletRequest req, HttpServletResponse resp) -> {
                            final var path = req.getRequestURI();
                            final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                            controller.removeById(id, resp);
                        });
                    }
                });
    }

    public void handleRoute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final var path = req.getRequestURI();
        final var method = req.getMethod();
        resp.setContentType("application/json");


        var doHandlers = handlers.get(method);

        for(Map.Entry<Pattern, RouteHandler> entry : doHandlers.entrySet()) {
            Matcher matcher = entry.getKey().matcher(path);
            if (matcher.matches()) {
                entry.getValue().handle(req, resp);
                return;
            }
        }
    }
}
