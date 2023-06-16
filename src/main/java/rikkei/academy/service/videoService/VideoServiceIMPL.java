package rikkei.academy.service.videoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rikkei.academy.model.Video;
import rikkei.academy.repository.IVideoRepository;

import java.util.List;
@Service
public class VideoServiceIMPL implements IVideoService{
    @Autowired
    private IVideoRepository videoRepository;
    @Override
    public List<Video> findAll() {
        return videoRepository.findAll();
    }

    @Override
    public Video findById(Long id) {
        return videoRepository.findById(id).get();
    }

    @Override
    public Video save(Video video) {
        return videoRepository.save(video);
    }

    @Override
    public void delete(Long id) {
        videoRepository.deleteById(id);
    }


    @Override
    public List<Video> searchVideoByTitle(String title) {
        return videoRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public Page<Video> findAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        return videoRepository.findAll(pageable);
    }

}
