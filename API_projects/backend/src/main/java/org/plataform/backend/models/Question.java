package org.plataform.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {

    @Id
    @Column(name = "id_question")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_level", nullable = false)
    private Level level;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_subject", nullable = false)
    private Subject subject;

    @Column(name = "text", nullable = false, length = 1500)
    private String text;

    @Column(name = "option_a", nullable = false) private String optionA;
    @Column(name = "option_b", nullable = false) private String optionB;
    @Column(name = "option_c", nullable = false) private String optionC;
    @Column(name = "option_d", nullable = false) private String optionD;
    @Column(name = "option_e", nullable = false) private String optionE;

    @Column(name = "correct", nullable = false, length = Integer.MAX_VALUE)
    private String correct;

    @PrePersist
    public void prePersist() {
        if (this.id == null) this.id = UUID.randomUUID();
    }
}
