package rikkei.academy.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rikkei.academy.model.User;

import java.util.Optional;

@Repository

public interface IUserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
