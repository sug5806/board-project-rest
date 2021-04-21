package hose.boardrestapi.controller;

import hose.boardrestapi.dto.PostDTO;
import hose.boardrestapi.service.PostService;
import hose.boardrestapi.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("post/{id}")
    public String getPost(@PathVariable(name = "id") Long id) {
        return "hello";
    }

    @PostMapping("/post")
    public ResponseEntity<Response> createPost(@Valid @RequestBody PostDTO postDTO) {
        Long postId = postService.createPost(postDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        PostDTO postDTOResponse = PostDTO.builder().id(postId).build();

        Response response = new Response("success", postDTOResponse);

        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }
}
