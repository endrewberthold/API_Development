package org.plataform.backend.dtos;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionResultDTO {
    private UUID userUuid;
    private UUID questionId;
    private String selectedOption;
}
