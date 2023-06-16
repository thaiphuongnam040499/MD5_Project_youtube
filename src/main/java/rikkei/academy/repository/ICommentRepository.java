package rikkei.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rikkei.academy.model.Comment;
@Repository
public interface ICommentRepository extends JpaRepository<Comment,Long> {
    Comment findCommentByVideoId(Long videoId);
}
