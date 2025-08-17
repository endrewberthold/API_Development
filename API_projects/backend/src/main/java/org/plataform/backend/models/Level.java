package org.plataform.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;


import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "levels")
public class Level {

    @Id
    @Column(name = "id_level")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_module", nullable = false)
    private Module idModule;

    @Column(name = "order_num", nullable = false)
    private Short orderNum;

    @ColumnDefault("0")
    @Column(name = "required_score", nullable = false)
    private Double requiredScore;

    @PrePersist
    public void prePersist() {
        if (this.id == null) this.id = UUID.randomUUID();
    }

}