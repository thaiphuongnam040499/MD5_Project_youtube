package rikkei.academy.service.channelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.model.Channel;
import rikkei.academy.repository.IChanelRepository;

import java.util.List;
@Service
public class ChannelServiceIMPL implements IChannelService{
    @Autowired
    private IChanelRepository chanelRepository;
    @Override
    public List<Channel> findAll() {
        return chanelRepository.findAll();
    }

    @Override
    public Channel findById(Long id) {
        return chanelRepository.findById(id).get();
    }

    @Override
    public Channel save(Channel channel) {
        return chanelRepository.save(channel);
    }

    @Override
    public void delete(Long id) {
        chanelRepository.deleteById(id);
    }
}
