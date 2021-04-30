package hose.boardrestapi.repository.post;

import hose.boardrestapi.dto.SearchDTO;
import hose.boardrestapi.entity.post.Post;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> postListQueryDSL(SearchDTO searchDTO);
}
