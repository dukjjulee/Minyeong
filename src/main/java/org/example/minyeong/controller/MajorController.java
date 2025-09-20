package org.example.minyeong.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.minyeong.dto.MajorRequestDto;
import org.example.minyeong.dto.MajorResponseDto;
import org.example.minyeong.service.MajorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MajorController {

    private final MajorService majorService;

    @PostMapping("/majors")
    public ResponseEntity<MajorResponseDto> save(
            @Valid @RequestBody MajorRequestDto majorRequestDto
    ) {
        return ResponseEntity.ok(majorService.save(majorRequestDto));
    }

    @GetMapping("/majors")
    public ResponseEntity<List<MajorResponseDto>> get(){
        return ResponseEntity.ok(majorService.getAll());
    }

    @GetMapping("/majors/{id}")
    public ResponseEntity<MajorResponseDto> getId(@PathVariable Long id){
        return ResponseEntity.ok(majorService.getById(id));
    }

    @PutMapping("/majors/{id}")
    public ResponseEntity<MajorResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody MajorRequestDto majorRequestDto
    ){
        return ResponseEntity.ok(majorService.updateId(id, majorRequestDto));
    }

    @DeleteMapping("/majors/{id}")
    public ResponseEntity<String> delet(@PathVariable Long id){
        majorService.delectId(id);
        return ResponseEntity.ok("삭제 완료");
    }
}
