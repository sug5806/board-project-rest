package hose.boardrestapi.controller;

import hose.boardrestapi.dto.CommentDTO;
import hose.boardrestapi.service.CommentService;
import hose.boardrestapi.util.response.sucess.SuccessResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Api(tags = {"댓글 관련 API"})
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/post/{id}/comment")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "댓글 쓰기", notes = "id에 해당하는 게시글에 댓글을 작성합니다.")
    public SuccessResponse<String> createComment(@PathVariable(name = "id") Long postId,
                                                 @Valid @RequestBody CommentDTO commentDTO,
                                                 Principal principal) {

        CommentDTO comment = commentService.createComment(postId, commentDTO, principal.getName());

        return SuccessResponse.success(null);

    }
}
