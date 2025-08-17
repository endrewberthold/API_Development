package org.plataform.backend.services;

import jakarta.persistence.Version;
import org.plataform.backend.dtos.FlashcardCreateDTO;
import org.plataform.backend.dtos.FlashcardDTO;
import org.plataform.backend.models.*;
import org.plataform.backend.repositories.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FlashcardService {

    private final FlashcardRepository flashcardRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final QuestionRepository questionRepository;

    public FlashcardService(FlashcardRepository flashcardRepository,
                            UserRepository userRepository,
                            SubjectRepository subjectRepository,
                            QuestionRepository questionRepository) {
        this.flashcardRepository = flashcardRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.questionRepository = questionRepository;
    }

    //Cria o flashcard a partir dos dados enviados pelo front expondo o UUID do user
    @Transactional
    public FlashcardDTO create(FlashcardCreateDTO dto) {
        User user = userRepository.findByUuidUser(dto.getUserUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subject not found"));

        Question question = null;
        if (dto.getQuestionId() != null) {
            question = questionRepository.findById(dto.getQuestionId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found"));
        }

        //monta a entidade
        Flashcard f = Flashcard.builder()
                .user(user)
                .concept(dto.getConcept())
                .description(dto.getDescription())
                .subject(subject)
                .question(question)
                .build();

        Flashcard saved = flashcardRepository.save(f);
        return toDto(saved);
    }

    //retorna todos os flashcards do user
    //importante ter paginação no front
    @Transactional(readOnly = true)
    public List<FlashcardDTO> listByUserUuid(UUID userUuid) {
        List<Flashcard> list = flashcardRepository.findByUser_UuidUser(userUuid);
        return list.stream().map(this::toDto).collect(Collectors.toList());
    }

    //edita um flashcard ja existente, busca por id
    //TODO um flashcard criado pertence somente a um usuário
    @Version
    @Transactional
    public FlashcardDTO update(UUID flashcardId, FlashcardCreateDTO dto) {
        Flashcard existing = flashcardRepository.findById(flashcardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flashcard not found"));

        //TODO acrescentar checagem de autorização para não haver transferencia de flashcards.
        if (dto.getUserUuid() != null && !existing.getUser().getUuidUser().equals(dto.getUserUuid())) {
            // revisar
            User newUser = userRepository.findByUuidUser(dto.getUserUuid())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            existing.setUser(newUser);
        }

        if (dto.getConcept() != null) existing.setConcept(dto.getConcept());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());

        if (dto.getSubjectId() != null) {
            Subject subject = subjectRepository.findById(dto.getSubjectId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subject not found"));
            existing.setSubject(subject);
        }

        if (dto.getQuestionId() != null) {
            Question question = questionRepository.findById(dto.getQuestionId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found"));
            existing.setQuestion(question);
        } else {
            existing.setQuestion(null);
        }

        Flashcard saved = flashcardRepository.save(existing);
        return toDto(saved);
    }

    //deleta um flashcard
    @Transactional
    public void delete(UUID flashcardId) {
        if (!flashcardRepository.existsById(flashcardId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flashcard not found");
        }
        flashcardRepository.deleteById(flashcardId);
    }

    //converte entidades JPA para DTO, isso isola o contrato de API e não expõe entidades JPA
    public FlashcardDTO toDto(Flashcard f) {
        return FlashcardDTO.builder()
                .id(f.getId())
                .concept(f.getConcept())
                .description(f.getDescription())
                .createDate(f.getCreateDate())
                .userUuid(f.getUser() != null ? f.getUser().getUuidUser() : null)
                .subjectId(f.getSubject() != null ? f.getSubject().getId() : null)
                .questionId(f.getQuestion() != null ? f.getQuestion().getId() : null)
                .build();
    }
}