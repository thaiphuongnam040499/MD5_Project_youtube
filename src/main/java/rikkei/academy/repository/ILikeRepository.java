package rikkei.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rikkei.academy.model.Like;

import java.util.Optional;

@Repository
public interface ILikeRepository extends JpaRepository<Like, Long> {
    Like findLikeByVideoId(Long videoId);
    Optional<Like> findLikeByUserIdAndVideoId(Long userId, Long videoId);
    @Query("SELECT COUNT(l) FROM Like l WHERE l.id = :videoId")
    Long countLikesByVideoId(@Param("videoId") Long videoId);
}
