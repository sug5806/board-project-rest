package hose.boardrestapi.util.paging;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class PageRequestCustom {
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int MAXIMUM_PAGE_SIZE = 50;

    private int page = 1;
    private int size = 15;
    private Sort.Direction direction = Sort.Direction.DESC;
    private String sort = "createdAt";

    public PageRequest of() {
        if (getPage() < 1) {
            this.page = 1;
        }

        if (size < DEFAULT_PAGE_SIZE) {
            this.size = DEFAULT_PAGE_SIZE;
        } else if (size > MAXIMUM_PAGE_SIZE) {
            this.size = MAXIMUM_PAGE_SIZE;
        }

        return PageRequest.of(page - 1, size, direction, sort);
    }
}
