package org.plataform.backend.repositories;

import org.plataform.backend.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {

    @Query("SELECT q FROM Question q JOIN q.level l " +
            "WHERE (:subjectId IS NULL OR q.subject.id = :subjectId) " +
            "AND l.orderNum <= :maxLevel")
    Page<Question> findAvailableQuestions(@Param("subjectId") UUID subjectId,
                                          @Param("maxLevel") short maxLevel,
                                          Pageable pageable);
}
