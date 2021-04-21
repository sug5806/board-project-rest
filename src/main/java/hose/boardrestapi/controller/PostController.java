package hose.boardrestapi.controller;

import hose.boardrestapi.dto.PostDTO;
import hose.boardrestapi.service.PostService;
import hose.boardrestapi.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("post/{id}")
    public ResponseEntity<ResponseUtil> getPost(@PathVariable(name = "id") Long id) {
        PostDTO post = postService.getPost(id);

        return ResponseUtil.success(post, HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<ResponseUtil> createPost(@Valid @RequestBody PostDTO postDTO) {
        PostDTO post = postService.createPost(postDTO);

        return ResponseUtil.success(post, HttpStatus.CREATED);
    }
}
