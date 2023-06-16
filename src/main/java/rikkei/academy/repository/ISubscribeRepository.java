package rikkei.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rikkei.academy.model.Subscribe;

import java.util.List;
import java.util.Optional;
@Repository
public interface ISubscribeRepository extends JpaRepository<Subscribe,Long> {
    Optional<Subscribe> findSubscribeByChannelId(Long channelId);
    Optional<Subscribe> findSubscribeByChannelIdAndUserId(Long channelId, Long userId);
    @Query("select COUNT(s) from Subscribe s where s.id = :channelId")
    Long countSubscribeByChannelId(Long channelId);
}
