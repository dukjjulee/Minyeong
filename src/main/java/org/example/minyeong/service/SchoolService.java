package org.example.minyeong.service;

import lombok.RequiredArgsConstructor;
import org.example.minyeong.dto.SchoolRequestDto;
import org.example.minyeong.dto.SchoolResponseDto;
import org.example.minyeong.entity.SchoolEntity;
import org.example.minyeong.repository.SchoolRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;

    @Transactional
    //SchoolResponseDto로 반환/ 저장(받은 데이터 가져와서)
    public SchoolResponseDto save(SchoolRequestDto schoolRequestDto){
        //SchoolEntity 타입의 schoolEntity에 SchoolEntity 적용한다.
        SchoolEntity saveSchoolEntity = new SchoolEntity(
                schoolRequestDto.getMajor(),
                schoolRequestDto.getGrade()
        );

        //SchoolEntity 타입의 schoolEntity에 schoolRepository 저장
        SchoolEntity schoolEntity = schoolRepository.save(saveSchoolEntity);
        //응답할 result 선언해 SchoolResponseDto 넣기
        SchoolResponseDto result = new SchoolResponseDto(
                schoolEntity.getId(),
                schoolEntity.getMajor(),
                schoolEntity.getGrade()
        );
        //result 반환
        return result;
    }

    @Transactional(readOnly = true)
    //List<SchoolResponseDto>/ 전부 가져오기
    public List<SchoolResponseDto> getAll() {
        //List<SchoolEntity> 타입의 schoolEntities 데이터베이스 다 가져와 적용한다
        List<SchoolEntity> schoolEntities = schoolRepository.findAll();
        //List<SchoolResponseDto> 인 dtos 선언
        List<SchoolResponseDto> dtos = new ArrayList<>();

        //schoolEntity 엔티티들 다 조회 후 변환
        for(SchoolEntity schoolEntity : schoolEntities) {
            SchoolResponseDto schoolResponseDto = new SchoolResponseDto(
                    schoolEntity.getId(),
                    schoolEntity.getMajor(),
                    schoolEntity.getGrade()
            );
            //dtos에 추가
            dtos.add(schoolResponseDto);
        }
        return dtos;
    }

    //단건조회
    @Transactional(readOnly = true)
    //SchoolResponseDto 로 반환/ (id) 를 가져옴
    public SchoolResponseDto getById(Long id) {
        //School타입의 schoolEntity에 데이터 베이스에서 전달 받은 id와 일치하는 데이터를 찾아옴
        SchoolEntity schoolEntity = schoolRepository.findById(id).orElseThrow(
                //다를 경우 경고문구
                () -> new IllegalArgumentException("존재하지않는 정보입니다")
        );
        //SchoolResponseDto 반환
        return new SchoolResponseDto(
                schoolEntity.getId(),
                schoolEntity.getMajor(),
                schoolEntity.getGrade()
        );
    }


    @Transactional
    //SchoolResponseDto로 반환한다/ 수정( id랑 SchoolRequestDto 의schoolRequestDto 를 )
    public SchoolResponseDto update(Long id, SchoolRequestDto schoolRequestDto) {
        //SchoolEntity 타입의 schoolEntity에 데이터베이스의 (전달밭은 id와 일치하는)id를 찾아 와 적용한다.
        SchoolEntity schoolEntity = schoolRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 정보 입니다.")
        );
        //엔티티의 수정(schoolRequestDto의 getMajor와 schoolRequestDto의getGrade())
        schoolEntity.updateSchool(schoolRequestDto.getMajor(), schoolRequestDto.getGrade());
        //반환한다 SchoolResponseDto
        return new SchoolResponseDto(
                schoolEntity.getId(),
                schoolEntity.getMajor(),
                schoolEntity.getGrade()
        );
    }

    @Transactional
    public void delete(Long id) {
        //SchoolEntity 타입의 schoolEntity에 데이터베이스의 (전달밭은 id와 일치하는)id를 찾아 와 삭제한다.
        SchoolEntity schoolEntity = schoolRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 정보 입니다.")
        );
        //schoolRepository의 삭제(특정아이디)
        schoolRepository.deleteById(id);
    }
}

