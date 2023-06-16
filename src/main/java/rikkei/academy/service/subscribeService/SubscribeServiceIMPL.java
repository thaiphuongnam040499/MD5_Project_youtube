package rikkei.academy.service.subscribeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.model.Subscribe;
import rikkei.academy.repository.ISubscribeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SubscribeServiceIMPL implements ISubscribeService{
    @Autowired
    private ISubscribeRepository subscribeRepository;
    @Override
    public List<Subscribe> findAll() {
        return null;
    }

    @Override
    public Subscribe findById(Long id) {
        return null;
    }

    @Override
    public Subscribe save(Subscribe subscribe) {
        return subscribeRepository.save(subscribe);
    }

    @Override
    public void delete(Long id) {
        subscribeRepository.deleteById(id);
    }

    @Override
    public Optional<Subscribe> findSubscribeByChannelId(Long channelId) {
        return subscribeRepository.findSubscribeByChannelId(channelId);
    }

    @Override
    public Optional<Subscribe> findSubscribeByChannelIdAndUserId(Long channelId, Long userId) {
        return subscribeRepository.findSubscribeByChannelIdAndUserId(channelId, userId);
    }

    @Override
    public Long countSubscribeByChannelId(Long channelId) {
        return subscribeRepository.countSubscribeByChannelId(channelId);
    }
}
