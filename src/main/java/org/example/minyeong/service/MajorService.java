package org.example.minyeong.service;

import lombok.RequiredArgsConstructor;
import org.example.minyeong.dto.MajorRequestDto;
import org.example.minyeong.dto.MajorResponseDto;
import org.example.minyeong.entity.MajorEntity;
import org.example.minyeong.entity.SchoolEntity;
import org.example.minyeong.repository.MajorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MajorService {

    private final MajorRepository majorRepository;

    @Transactional
    public MajorResponseDto save(MajorRequestDto majorRequestDto){

        MajorEntity saveMajorEntity = new MajorEntity(
                majorRequestDto.getMajorName(),
                majorRequestDto.getMajorUser(),
                majorRequestDto.getMajorProfessor()
        );

        MajorEntity majorEntity = majorRepository.save(saveMajorEntity);

        return new MajorResponseDto(
                majorEntity.getId(),
                majorEntity.getMajorName(),
                majorEntity.getMajorUser(),
                majorEntity.getMajorProfessor()
        );
    }
    @Transactional(readOnly = true)
    public List<MajorResponseDto> getAll(){
        List<MajorEntity> majorEntities = majorRepository.findAll();
        List<MajorResponseDto> dtos = new ArrayList<>();

        for(MajorEntity majorEntity : majorEntities){
            MajorResponseDto majorResponseDto = new MajorResponseDto(
                    majorEntity.getId(),
                    majorEntity.getMajorName(),
                    majorEntity.getMajorUser(),
                    majorEntity.getMajorProfessor()
            );
            dtos.add(majorResponseDto);
        }
        return dtos;
    }

    @Transactional(readOnly = true)
    public MajorResponseDto getById(Long id) {
        MajorEntity majorEntity = majorRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 Major가 없습니다.")
        );

        return new MajorResponseDto(
                majorEntity.getId(),
                majorEntity.getMajorName(),
                majorEntity.getMajorUser(),
                majorEntity.getMajorProfessor()
        );
    }

    @Transactional
    public MajorResponseDto updateId(Long id, MajorRequestDto majorRequestDto){

        MajorEntity majorEntity = majorRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 Major가 없습니다.")
        );

        majorEntity.updateMajor(
                majorRequestDto.getMajorName(),
                majorRequestDto.getMajorUser(),
                majorRequestDto.getMajorProfessor()
        );

        return new MajorResponseDto(
                majorEntity.getId(),
                majorEntity.getMajorName(),
                majorEntity.getMajorUser(),
                majorEntity.getMajorProfessor()
        );
    }

    @Transactional
    public void delectId(Long id) {
        MajorEntity majorEntity = majorRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 Major가 없습니다.")
        );
        majorRepository.deleteById(id);
    }
}
