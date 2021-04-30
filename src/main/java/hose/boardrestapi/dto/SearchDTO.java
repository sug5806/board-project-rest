package hose.boardrestapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class SearchDTO {
    @JsonProperty
    private String category;

    @JsonProperty("search_type")
    private String searchType = "title";

    @JsonProperty
    private String query;
}
