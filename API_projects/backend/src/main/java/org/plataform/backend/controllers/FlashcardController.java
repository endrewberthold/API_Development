package org.plataform.backend.controllers;

import org.plataform.backend.dtos.FlashcardCreateDTO;
import org.plataform.backend.dtos.FlashcardDTO;
import org.plataform.backend.services.FlashcardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/flashcards")
public class FlashcardController {

    private final FlashcardService flashcardService;

    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    @PostMapping
    public ResponseEntity<FlashcardDTO> create(@RequestBody FlashcardCreateDTO dto) {
        FlashcardDTO created = flashcardService.create(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/user/{userUuid}")
    public ResponseEntity<List<FlashcardDTO>> listByUser(@PathVariable UUID userUuid) {
        return ResponseEntity.ok(flashcardService.listByUserUuid(userUuid));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlashcardDTO> update(@PathVariable UUID id, @RequestBody FlashcardCreateDTO dto) {
        FlashcardDTO updated = flashcardService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        flashcardService.delete(id);
        return ResponseEntity.noContent().build();
    }
}