package hose.boardrestapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @GetMapping("post/{id}")
    public String getPost(@PathVariable(name = "id") Long id) {
        return "hello";
    }
}
