package joljak.homecoming.repository;

import joljak.homecoming.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByProvider(String provider);
    User findByName(String name);
}
