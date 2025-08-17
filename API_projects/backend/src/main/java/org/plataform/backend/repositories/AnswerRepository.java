package org.plataform.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.plataform.backend.models.Answer;

import java.util.UUID;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, UUID> {

    long countByUser_IdAndQuestion_Id(Long user_Id, UUID question_Id);

    @Query("SELECT COUNT(DISTINCT a.question.id) FROM Answer a JOIN a.question q JOIN q.level l " +
    "WHERE a.user.id = :userId AND a.isCorrect = true AND q.subject.id = :subjectId AND l.orderNum = :levelOrder")
    Long countDistinctCorrectByUserAndSubjectAndLevel(@Param("userId") Long userId,
                                                      @Param("subjectId") UUID subjectId,
                                                      @Param("levelOrder") short levelOrder);

    @Query("SELECT COUNT(DISTINCT a.question.id) FROM Answer  a JOIN a.question q JOIN q.level l " +
    "WHERE a.user.id = :userId AND a.isCorrect = true AND q.subject.id = :subjectId")
    Long countDistinctCorrectByUserAndSubject(@Param("userId") Long userId,
                                              @Param("subjectId") UUID subjectId);

}
