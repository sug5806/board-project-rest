package hose.boardrestapi.util.enumerate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum SearchType {
    TITLE("title", "포스트 제목으로 검색"),
    USER("user", "유저명으로 검색");

    @Getter
    private final String type;

    @Getter
    private final String description;

    public static SearchType convertToType(String stringType) {
        return Arrays.stream(values())
                .filter(searchType -> searchType.type.equals(stringType))
                .findAny()
                .orElse(TITLE);
    }
}
