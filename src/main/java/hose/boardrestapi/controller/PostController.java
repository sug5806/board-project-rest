package hose.boardrestapi.controller;

import hose.boardrestapi.dto.post.PostCategoryDTO;
import hose.boardrestapi.dto.post.PostDTO;
import hose.boardrestapi.service.PostService;
import hose.boardrestapi.util.response.sucess.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("post/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public SuccessResponse<PostDTO> getPost(@PathVariable(name = "id") Long id) {
        PostDTO post = postService.getPost(id);

        return SuccessResponse.success(post);
    }

    @PostMapping("/post")
    @ResponseStatus(value = HttpStatus.CREATED)
    public SuccessResponse<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {
        PostDTO post = postService.createPost(postDTO);

        return SuccessResponse.success(post);
    }

    @PutMapping("post/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public SuccessResponse<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable(name = "id") Long postId) {
        PostDTO postDTOResponse = postService.updatePost(postId, postDTO);

        return SuccessResponse.success(postDTOResponse);
    }

    @DeleteMapping("post/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public SuccessResponse<String> deletePost(@PathVariable(name = "id") Long postId) {
        postService.deletePost(postId);
        return SuccessResponse.success(null);
    }

    @GetMapping("/post-category")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public SuccessResponse<List<PostCategoryDTO>> postCategory() {
        List<PostCategoryDTO> postCategoryDTOS = postService.postCategoryList();

        return SuccessResponse.success(postCategoryDTOS);
    }
}
