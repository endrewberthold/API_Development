package org.plataform.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_subject_progress")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(UserSubjectProgress.UserSubjectProgressId.class)
public class UserSubjectProgress {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "subject_id")
    private UUID subjectId;

    @Column(name = "unlocked_level", nullable = false)
    private Short unlockedLevel;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.updatedAt == null) this.updatedAt = LocalDateTime.now();
        if (this.unlockedLevel == null)this.unlockedLevel = 1;
    }

    @Embeddable
    public static class UserSubjectProgressId implements Serializable {
        private Long userId;
        private UUID subjectId;

        public UserSubjectProgressId(){}
        public UserSubjectProgressId(Long userId, UUID subjectId) {
            this.userId = userId;
            this.subjectId = subjectId;
        }

        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UserSubjectProgressId that)) return false;
            return java.util.Objects.equals(userId, that.userId) &&
                    java.util.Objects.equals(subjectId, that.subjectId);
        }
        @Override public int hashCode() { return java.util.Objects.hash(userId, subjectId); }

    }
}
