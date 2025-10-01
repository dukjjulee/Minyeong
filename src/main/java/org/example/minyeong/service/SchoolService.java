package org.example.minyeong.service;

import lombok.RequiredArgsConstructor;
import org.example.minyeong.dto.SchoolRequestDto;
import org.example.minyeong.dto.SchoolResponseDto;
import org.example.minyeong.entity.SchoolEntity;
import org.example.minyeong.repository.SchoolRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;

    @Transactional
    //SchoolResponseDto로 반환/ 저장(받은 데이터 가져와서)
    public SchoolResponseDto save(SchoolRequestDto schoolRequestDto){
        //SchoolEntity 타입의 schoolEntity에 SchoolEntity 적용한다.
        SchoolEntity saveSchoolEntity = SchoolEntity.create(
                schoolRequestDto.getSchoolName()
        );

        //SchoolEntity 타입의 schoolEntity에 schoolRepository 저장
        SchoolEntity schoolEntity = schoolRepository.save(saveSchoolEntity);

        //응답할 result 선언해 SchoolResponseDto 넣기
        return SchoolResponseDto.from (schoolEntity);
    }

    @Transactional(readOnly = true)
    //List<SchoolResponseDto>/ 전부 가져오기
    public Page<SchoolResponseDto> getAll(String searchKeyword, Pageable pageable) {
        //List<SchoolEntity> 타입의 schoolEntities 데이터베이스 다 가져와 적용한다
        Page<SchoolEntity> schoolEntities = schoolRepository.findAllBySchoolName(searchKeyword, pageable);

        //schoolEntity 엔티티들 다 조회 후 변환
        return schoolEntities.map(e -> {
            return SchoolResponseDto.from(e);
        });
    }

    //단건조회
    @Transactional(readOnly = true)
    //SchoolResponseDto 로 반환/ (id) 를 가져옴
    public SchoolResponseDto getById(Long id) {
        //School타입의 schoolEntity에 데이터 베이스에서 전달 받은 id와 일치하는 데이터를 찾아옴
        SchoolEntity schoolEntity = getSchoolEntity(id);
        //응답할 result 선언해 SchoolResponseDto 넣기
        return SchoolResponseDto.from(schoolEntity);
    }

    @Transactional
    //SchoolResponseDto로 반환한다/ 수정( id랑 SchoolRequestDto 의schoolRequestDto 를 )
    public SchoolResponseDto update(Long id, SchoolRequestDto schoolRequestDto) {

        //SchoolEntity 타입의 schoolEntity에 데이터베이스의 (전달밭은 id와 일치하는)id를 찾아 와 적용한다.
        SchoolEntity schoolEntity = getSchoolEntity(id);

        schoolEntity.update(schoolRequestDto.getSchoolName());

        return SchoolResponseDto.from (schoolEntity);
    }

    @Transactional
    public void delete(Long id) {
        //SchoolEntity 타입의 schoolEntity에 데이터베이스의 (전달밭은 id와 일치하는)id를 찾아 와 삭제한다.
        SchoolEntity schoolEntity = getSchoolEntity(id);
        //schoolEntity의 getMan이 비었을 경우
        if(schoolEntity.getUserEntity().isEmpty()){
            schoolRepository.deleteById(id);//데이터베이스에서 전달받은 id의 데이터를 삭제한다.
        }
        else{
            throw new IllegalArgumentException("User 데이터가 남아 있습니다.");
        }
    }

    private SchoolEntity getSchoolEntity(Long id) {
        SchoolEntity schoolEntity = schoolRepository.findById(id).orElseThrow(
                //다를 경우 경고문구
                () -> new IllegalArgumentException("존재하지 않는 SchoolId 입니다")
        );
        return schoolEntity;
    }
}

