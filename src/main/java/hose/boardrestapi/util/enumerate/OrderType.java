package hose.boardrestapi.util.enumerate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum OrderType {
    POPULAR("popular", "최다 인기순"),
    CREATE("create", "최신 날짜순");

    private final String type;

    private final String description;

    public static OrderType convertToType(String stringOrderType) {
        return Arrays.stream(values())
                .filter(orderType -> orderType.type.equals(stringOrderType))
                .findAny()
                .orElse(CREATE);
    }
}
