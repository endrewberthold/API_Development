package org.plataform.backend.dtos;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlashcardCreateDTO {
    private UUID userUuid;     // uuid_user do usuário (externo)
    private String concept;
    private String description;
    private UUID subjectId;
    private UUID questionId;   // opcional, pode ser null
}
