package hose.boardrestapi.dto;

import lombok.Builder;
import lombok.Data;

import java.beans.ConstructorProperties;

@Builder
@Data
public class SearchDTO {
    private String category;
    private String searchType;
    private String query;

    @ConstructorProperties({"category", "search_type", "query"})
    public SearchDTO(String category, String searchType, String query) {
        this.category = category;
        this.searchType = searchType;
        this.query = query;
    }
}
