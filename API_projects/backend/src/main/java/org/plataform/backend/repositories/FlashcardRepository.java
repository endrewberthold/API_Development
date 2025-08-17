package org.plataform.backend.repositories;

import org.plataform.backend.models.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface FlashcardRepository extends JpaRepository<Flashcard, UUID> {

    List<Flashcard> findByUser_Id(Long userId);

    // opcional: buscar por uuid do usuário diretamente (passando o campo aninhado)
    List<Flashcard> findByUser_UuidUser(UUID uuidUser);
}
