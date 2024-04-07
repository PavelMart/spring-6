package ru.netology.router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@FunctionalInterface
public interface RouteHandler {
    void handle(HttpServletRequest req, HttpServletResponse res) throws IOException;
}
