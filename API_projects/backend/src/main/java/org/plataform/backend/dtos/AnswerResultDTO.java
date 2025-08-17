package org.plataform.backend.dtos;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerResultDTO {
    private UUID questionId;
    private boolean isCorrect;
    private int pointsGained;
    private int totalXp;
    private boolean levelUp;
    private int newLevel;
    private int unlockedLevelForSubject;
}
