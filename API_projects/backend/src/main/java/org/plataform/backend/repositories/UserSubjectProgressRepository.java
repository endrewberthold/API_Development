package org.plataform.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.plataform.backend.models.UserSubjectProgress;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSubjectProgressRepository extends JpaRepository<UserSubjectProgress, UserSubjectProgress.UserSubjectProgressId> {
}
