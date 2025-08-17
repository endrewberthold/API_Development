package org.plataform.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "skills")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skills {

    @Id
    @Column(name = "id_skill")
    private UUID id;

    @Column(name = "name_skill", nullable = false)
    private String nameSkill;

    @PrePersist
    public void prePersist() {
        if (this.id == null) this.id = UUID.randomUUID();
    }

}
