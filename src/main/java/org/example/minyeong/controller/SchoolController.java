package org.example.minyeong.controller;

import lombok.RequiredArgsConstructor;
import org.example.minyeong.dto.SchoolRequestDto;
import org.example.minyeong.dto.SchoolResponseDto;
import org.example.minyeong.service.SchoolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

    @PostMapping("/schools")
    //저장 후 응답을 ResponseEntity로 반환
    public ResponseEntity<SchoolResponseDto> save(
            //schoolRequestDto 확인해서
            @RequestBody SchoolRequestDto schoolRequestDto) {
        //ok 반환(schoolService 로 저장 후)
        return ResponseEntity.ok(schoolService.save(schoolRequestDto));
    }

    @GetMapping("/schools")
    //SchoolResponseDto 의 리스트 가져오기
    public ResponseEntity<List<SchoolResponseDto>> get(){
        //ok 반환(schoolService 가져와서)
        return ResponseEntity.ok(schoolService.getAll());
    }

    //단건 조회
    @GetMapping("/schools/{id}")
    //응답을 ResponseEntity 반환할건데, id를 가져와서
    public ResponseEntity<SchoolResponseDto> get(@PathVariable Long id){
        //id와 일치하는 데이터를 받아와 ok반환
        return ResponseEntity.ok(schoolService.getById(id));
    }

    //수정
    @PutMapping("/schools/{id}")
    //응답을 ResponseEntity로 반환할건데 수정(id를 가져오고 schoolRequestDto 확인한 후)
    public ResponseEntity<SchoolResponseDto> update(@PathVariable Long id, @RequestBody SchoolRequestDto schoolRequestDto) {
        //ok 반환(수정한 id, schoolRequestDto 가져와서)
        return ResponseEntity.ok(schoolService.update(id, schoolRequestDto));
    }

    @DeleteMapping("/schools/{id}")
    //응답을 ResponseEntity<String>으로 반환할건데 삭제 (id 받아와서)
    public ResponseEntity<String> delete(@PathVariable Long id){
        //schoolService 가 (특정 id)삭제
        schoolService.delete(id);
        return ResponseEntity.ok("삭제 완료");
    }
}
