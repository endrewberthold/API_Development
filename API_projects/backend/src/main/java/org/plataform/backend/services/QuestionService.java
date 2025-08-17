package org.plataform.backend.services;

import org.plataform.backend.repositories.AnswerRepository;
import org.plataform.backend.repositories.QuestionRepository;
import org.plataform.backend.repositories.UserRepository;
import org.plataform.backend.repositories.UserSubjectProgressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private final UserSubjectProgressRepository progressRepository;

    public QuestionService(QuestionRepository questionRepository,
                           UserRepository userRepository,
                           AnswerRepository answerRepository,
                           UserSubjectProgressRepository progressRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
        this.progressRepository = progressRepository;
    }

    @Transactional(readOnly = true)
    public short determineUnlockedLevel(Long userId, UUID subjectId) {

        return progressRepository(findByUserIdAndSubjectId(userId, subjectId)
                .map(p -> p.getUnlockedLevel())
                .orElseGet(() -> {
                    short unlocked = 1;
                    Short maxLevelOrder = questionRepository.findTopBySubjectLevelOrderDesc(subjectId)
                            .map(q -> q.getLevel().getOrderNum())
                            .orElse((short)1);

                    for (short lvl = 1; lvl <= maxLeverOrder; lvl++) {
                        Long cnt = answerRepository.countDistinctCorrectByUserAndSubjectAndLevel(userId, subjectId, lvl);
                        if (cnt != null && cnt >= 5) {
                            unlocked = (short) (lvl + 1);
                        } else {
                            break;
                        }
                    }
                    return unlocked;
                });
    }

}
