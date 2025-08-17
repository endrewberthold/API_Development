package org.plataform.backend.repositories;

import org.plataform.backend.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SubjectRepository extends JpaRepository<Subject, UUID> { }
