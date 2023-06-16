package rikkei.academy.service.videoService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rikkei.academy.model.Video;
import rikkei.academy.service.IGenericService;

import java.util.List;

public interface IVideoService extends IGenericService<Video,Long> {
    List<Video> searchVideoByTitle(String title);
    Page<Video> findAll(int pageNumber, int pageSize);
}
