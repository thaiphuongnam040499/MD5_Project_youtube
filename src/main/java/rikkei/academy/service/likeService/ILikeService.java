package rikkei.academy.service.likeService;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rikkei.academy.model.Like;
import rikkei.academy.service.IGenericService;

import java.util.Optional;

public interface ILikeService extends IGenericService<Like,Long> {
    Like findLikeByVideoId(Long videoId);
    Optional<Like> findLikeByUserIdAndVideoId(Long userId, Long videoId);
    @Query("SELECT COUNT(l) FROM Like l WHERE l.id = :videoId")
    Long countLikesByVideoId(@Param("videoId") Long videoId);
}
