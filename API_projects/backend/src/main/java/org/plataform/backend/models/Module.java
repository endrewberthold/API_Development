package org.plataform.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "modules")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Module {

    @Id
    @Column(name = "id_module")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_skill", nullable = false)
    private Skills idSkill;

    @Column(name = "name_module", nullable = false)
    private String nameModule;

    @Column(name = "order_num", nullable = false)
    private Short orderNum;

    @PrePersist
    public void prePersist() {
        if (this.id == null) this.id = UUID.randomUUID();
    }

}
