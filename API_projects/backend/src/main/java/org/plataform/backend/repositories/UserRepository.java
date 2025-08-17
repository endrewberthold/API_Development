package org.plataform.backend.repositories;

import org.plataform.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUuidUser(UUID uuidUser);
}
