package rikkei.academy.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rikkei.academy.model.Video;

import java.util.List;
@Repository
public interface IVideoRepository extends JpaRepository<Video,Long> {
   List<Video> findByTitleContainingIgnoreCase(String title);
   Page<Video> findAll(Pageable pageable);
}
