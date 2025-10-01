package org.example.minyeong.service;

import lombok.RequiredArgsConstructor;
import org.example.minyeong.dto.MajorRequestDto;
import org.example.minyeong.dto.MajorResponseDto;
import org.example.minyeong.dto.SchoolResponseDto;
import org.example.minyeong.entity.MajorEntity;
import org.example.minyeong.entity.SchoolEntity;
import org.example.minyeong.repository.MajorRepository;
import org.example.minyeong.repository.SchoolRepository;
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

        MajorEntity saveMajorEntity = MajorEntity.create(
                majorRequestDto.getMajorName(),
                majorRequestDto.getMajorProfessor()
        );

        MajorEntity majorEntity = majorRepository.save(saveMajorEntity);

        return MajorResponseDto.from(majorEntity);
    }

    @Transactional(readOnly = true)
    public List<MajorResponseDto> getAll() {
        List<MajorEntity> majorEntities = majorRepository.findAll();

        return majorEntities.stream().map(majorEntity -> {
            return MajorResponseDto.from(majorEntity);
        }).toList();
    }


    @Transactional(readOnly = true)
    public MajorResponseDto getById(Long id) {
        MajorEntity majorEntity = getMajorEntity(id);
//
//        List<SchoolResponseDto> schoolResponseDtos = majorEntity.getSchoolMajors().stream()
//                .map(it -> {
//                    SchoolEntity schoolEntity = it.getSchoolEntity();
//                    return new SchoolResponseDto(
//                            schoolEntity.getId(),
//                            schoolEntity.getSchoolName()
//                    );
//
//                }).toList();

        return MajorResponseDto.from(majorEntity);
    }

    @Transactional
    public MajorResponseDto updateId(Long id, MajorRequestDto majorRequestDto){

        MajorEntity majorEntity = getMajorEntity(id);

        majorEntity.updateMajor(
                majorRequestDto.getMajorName(),
                majorRequestDto.getMajorProfessor()
        );
//
//        List<SchoolResponseDto> schoolResponseDtos = majorEntity.getSchoolMajors().stream()
//                .map(it -> {
//                    SchoolEntity schoolEntity = it.getSchoolEntity();
//                    return new SchoolResponseDto(
//                            schoolEntity.getId(),
//                            schoolEntity.getSchoolName()
//                    );
//
//                }).toList();
        
        return MajorResponseDto.from(majorEntity);
    }

    @Transactional
    public void delectId(Long id) {
        MajorEntity majorEntity = getMajorEntity(id);
//        majorRepository.deleteById(id);
        majorRepository.delete(majorEntity);
    }

    private MajorEntity getMajorEntity(Long id) {
        MajorEntity majorEntity = majorRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 Major가 없습니다.")
        );
        return majorEntity;
    }
}
