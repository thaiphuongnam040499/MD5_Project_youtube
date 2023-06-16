package rikkei.academy.service.userService;

import rikkei.academy.model.User;
import rikkei.academy.service.IGenericService;

import java.util.Optional;

public interface IUserService extends IGenericService<User,Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
}
