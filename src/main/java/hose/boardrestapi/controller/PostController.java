package hose.boardrestapi.controller;

import hose.boardrestapi.dto.PostDTO;
import hose.boardrestapi.service.PostService;
import hose.boardrestapi.util.response.sucess.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("post/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public SuccessResponse getPost(@PathVariable(name = "id") Long id) {
        PostDTO post = postService.getPost(id);

        return SuccessResponse.success(post);
    }

    @PostMapping("/post")
    @ResponseStatus(value = HttpStatus.CREATED)
    public SuccessResponse createPost(@Valid @RequestBody PostDTO postDTO) {
        PostDTO post = postService.createPost(postDTO);

        return SuccessResponse.success(post);
    }
}