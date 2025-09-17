package org.ilestegor.lab1.repository;

import org.ilestegor.lab1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String name);

    boolean existsUsersByUserName(String name);
}
