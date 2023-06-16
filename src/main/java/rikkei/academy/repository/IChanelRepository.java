package rikkei.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rikkei.academy.model.Channel;
import rikkei.academy.model.User;
@Repository
public interface IChanelRepository extends JpaRepository<Channel,Long> {
}
