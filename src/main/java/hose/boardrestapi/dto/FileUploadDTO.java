package hose.boardrestapi.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileUploadDTO {
    private String url;
}
