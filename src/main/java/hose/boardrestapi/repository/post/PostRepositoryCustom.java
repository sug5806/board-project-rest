package hose.boardrestapi.repository.post;

import hose.boardrestapi.dto.SearchDTO;
import hose.boardrestapi.entity.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> postListQueryDSL(SearchDTO searchDTO);

    Page<Post> postListPagingQueryDSL(SearchDTO searchDTO, Pageable pageable);
}
