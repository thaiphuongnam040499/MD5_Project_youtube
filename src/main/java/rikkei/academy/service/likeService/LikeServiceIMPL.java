package rikkei.academy.service.likeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.model.Like;
import rikkei.academy.repository.ILikeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LikeServiceIMPL implements ILikeService{
    @Autowired
    private ILikeRepository likeRepository;
    @Override
    public List<Like> findAll() {
        return null;
    }

    @Override
    public Like findById(Long id) {
        return null;
    }

    @Override
    public Like save(Like like) {
        return likeRepository.save(like);
    }

    @Override
    public void delete(Long id) {
        likeRepository.deleteById(id);
    }

    @Override
    public Like findLikeByVideoId(Long videoId) {
        return likeRepository.findLikeByVideoId(videoId);
    }

    @Override
    public Optional<Like> findLikeByUserIdAndVideoId(Long userId, Long videoId) {
        return likeRepository.findLikeByUserIdAndVideoId(userId, videoId);
    }

    @Override
    public Long countLikesByVideoId(Long videoId) {
        return likeRepository.countLikesByVideoId(videoId);
    }
}
