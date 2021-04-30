package hose.boardrestapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class SearchDTO {
    private String category = "free";

    @JsonProperty("search_type")
    private String searchType = "title";

    private String query = "";
}
