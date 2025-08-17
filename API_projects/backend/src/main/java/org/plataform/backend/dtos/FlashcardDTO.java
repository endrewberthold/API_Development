package org.plataform.backend.dtos;

import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlashcardDTO {
    private UUID id;
    private String concept;
    private String description;
    private LocalDateTime createDate;
    private UUID userUuid;     // expor UUID do usuário
    private UUID subjectId;
    private UUID questionId;   // pode ser null
}
