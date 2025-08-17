package org.plataform.backend.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "id_user")
    private Long id; // gerado pelo DB (IDENTITY)

    @Column(name = "uuid_user", nullable = false, unique = true)
    private UUID uuidUser;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

    @Column(name = "xp_points", nullable = false)
    private Integer xpPoints;

    @Column(name = "level", nullable = false)
    private Integer level;

    @PrePersist
    public void prePersist() {
        if (this.uuidUser == null) this.uuidUser = UUID.randomUUID();
        if (this.createAt == null) this.createAt = LocalDateTime.now();
        if (this.xpPoints == null) this.xpPoints = 0;
        if (this.level == null) this.level = 1;
    }
}
