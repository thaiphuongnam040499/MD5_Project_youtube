package rikkei.academy.service.subscribeService;

import org.springframework.data.jpa.repository.Query;
import rikkei.academy.model.Subscribe;
import rikkei.academy.service.IGenericService;

import java.util.Optional;

public interface ISubscribeService extends IGenericService<Subscribe,Long> {
    Optional<Subscribe> findSubscribeByChannelId(Long channelId);
    Optional<Subscribe> findSubscribeByChannelIdAndUserId(Long channelId, Long userId);
    @Query("select COUNT(s) from Subscribe s where s.id = :channelId")
    Long countSubscribeByChannelId(Long channelId);
}
