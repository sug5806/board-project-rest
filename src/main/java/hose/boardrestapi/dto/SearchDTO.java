package hose.boardrestapi.dto;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class SearchDTO {
    private String category;
    private String searchType;
    private String query;
}
