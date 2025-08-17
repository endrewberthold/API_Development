package org.plataform.backend.dtos;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionReadDTO {
    private UUID id;
    private UUID subjectId;
    private Short levelOrder;
    private String text;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String optionE;
}
