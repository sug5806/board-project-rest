package hose.boardrestapi.controller;

import hose.boardrestapi.dto.SearchDTO;
import hose.boardrestapi.dto.post.PostCategoryDTO;
import hose.boardrestapi.dto.post.PostDTO;
import hose.boardrestapi.service.PostService;
import hose.boardrestapi.util.paging.PageRequestCustom;
import hose.boardrestapi.util.response.sucess.SuccessResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = {"게시물 관련 API"})
@Slf4j
public class PostController {
    private final PostService postService;

//    @GetMapping("/board/post-list")
//    @ResponseStatus(value = HttpStatus.OK)
//    @ApiOperation(value = "게시물 목록 조회", notes = "게시물 목록을 조회합니다.")
//    public SuccessResponse<List<PostDTO>> getPostList(SearchDTO searchDTO) {
//        List<PostDTO> postList = postService.getPostList(searchDTO);
//
//        return SuccessResponse.success(postList);
//    }

    @GetMapping("/board/post-list")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "게시물 목록 조회", notes = "게시물 목록을 조회합니다.")
    public SuccessResponse<Page<PostDTO>> getPostList(SearchDTO searchDTO, PageRequestCustom pageRequestCustom) {
        Page<PostDTO> postListPaging = postService.getPostListPaging(searchDTO, pageRequestCustom.of());

        return SuccessResponse.success(postListPaging);
    }

    @GetMapping("post/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "게시물 조회", notes = "게시물을 조회합니다.")
    public SuccessResponse<PostDTO> getPost(@PathVariable(name = "id") Long id) {
        PostDTO post = postService.getPost(id);

        return SuccessResponse.success(post);
    }

    @PostMapping("/post")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "게시물 생성", notes = "게시물을 생성합니다.")
    public SuccessResponse<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO, Principal principal) {
        log.info(principal.getName());
        PostDTO post = postService.createPost(postDTO, principal.getName());

        return SuccessResponse.success(post);
    }

    @PutMapping("post/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "게시물 수정", notes = "게시물을 수정합니다.")
    public SuccessResponse<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO,
                                               @PathVariable(name = "id") Long postId,
                                               Principal principal) {
        PostDTO postDTOResponse = postService.updatePost(postId, postDTO, principal.getName());

        return SuccessResponse.success(postDTOResponse);
    }

    @DeleteMapping("post/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "게시물 삭제", notes = "게시물을 삭제합니다.")
    public SuccessResponse<String> deletePost(@PathVariable(name = "id") Long postId,
                                              Principal principal) {
        postService.deletePost(postId, principal.getName());
        return SuccessResponse.success(null);
    }

    @GetMapping("/post-category")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "게시물 카테고리 조회", notes = "게시물 카테고리를 조회합니다.")
    public SuccessResponse<List<PostCategoryDTO>> postCategory() {
        List<PostCategoryDTO> postCategoryDTOList = postService.postCategoryList();

        return SuccessResponse.success(postCategoryDTOList);
    }

    @PostMapping("/post/{id}/like")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "게시물 좋아요", notes = "게시물 좋아요 기능입니다.")
    public SuccessResponse<String> postLike(@PathVariable(name = "id") Long postId, Principal principal) {
        postService.postLike(postId, principal.getName());
        return SuccessResponse.success(null);
    }
}
