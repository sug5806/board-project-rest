package hose.boardrestapi.controller;

import hose.boardrestapi.dto.FileUploadDTO;
import hose.boardrestapi.service.FileService;
import hose.boardrestapi.util.response.sucess.SuccessResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/image/upload")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "이미지 업로드하기", notes = "게시글에 사용할 이미지를 업로드 합니다.", code = 200, httpMethod = "POST")
    public SuccessResponse<FileUploadDTO> imageUpload(@RequestPart MultipartFile file, Principal principal) throws IOException {
        FileUploadDTO fileUploadDTO = fileService.fileUpload(file, principal.getName());
        return SuccessResponse.success(fileUploadDTO);
    }

}
