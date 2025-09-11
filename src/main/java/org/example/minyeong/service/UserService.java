package org.example.minyeong.service;

import lombok.RequiredArgsConstructor;
import org.example.minyeong.dto.MajorResponseDto;
import org.example.minyeong.dto.UserRequestDto;
import org.example.minyeong.dto.UserResponseDto;
import org.example.minyeong.dto.SchoolResponseDto;
import org.example.minyeong.entity.MajorEntity;
import org.example.minyeong.entity.SchoolEntity;
import org.example.minyeong.entity.SchoolMajor;
import org.example.minyeong.entity.UserEntity;
import org.example.minyeong.repository.MajorRepository;
import org.example.minyeong.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service//서비스 bean
@RequiredArgsConstructor
public class UserService {//MenService 선언
    //비공 | 고정 |     타입     |     변수     | 객체를 만들어 초기화
    private final UserRepository userRepository;
    private final MajorRepository majorRepository;

    @Transactional
    public UserResponseDto save(UserRequestDto userRequestDto){

        //schoolEntity 에 id로 데이터를 찾아온다 다를 시 경고
        MajorEntity majorEntity = majorRepository.findById(userRequestDto.getMajorId()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 majorId 입니다.")
        );
        //saveUserEntity 에 manRequest 담기
        UserEntity saveUserEntity = new UserEntity(
                userRequestDto.getName(),
                userRequestDto.getGender(),
                userRequestDto.getAge(),
                majorEntity
        );

        //UserEntity 타입의 userEntity 에 saveManEntity의 값을 데이터베이스에서 가져와 적용
        UserEntity userEntity = userRepository.save(saveUserEntity);
        List<SchoolMajor> schoolMajor = majorEntity.getSchoolMajors();

        // schoolEntity 를 schoolResponseDto 에 담기
        MajorResponseDto majorResponseDto = new MajorResponseDto (
                majorEntity.getId(),
                majorEntity.getMajorName(),
                majorEntity.getMajorProfessor(),
                schoolMajor.stream().map(it -> {
                            MajorEntity major = it.getMajorEntity();
                            return new MajorResponseDto(
                                    major.getId(),
                                    major.getMajorName(),
                                    major.getMajorProfessor()
                            );
                        }
                ).toList()

        );
        // UserResponseDto 반환
        return new UserResponseDto(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getGender(),
                userEntity.getAge(),
                majorResponseDto
        );
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getAll(){

        List<UserEntity> userEntities = userRepository.findAll();
        List<UserResponseDto> dtos = new ArrayList<>();

        //반복문으로 리스트 전체 조회
        for (UserEntity userEntity : userEntities) {
            SchoolEntity schoolEntity = userEntity.getSchoolEntity();
            List<SchoolMajor> schoolMajor = schoolEntity.getSchoolMajors();

            //schoolResponseDto선언 후 SchoolResponseDto 데이터 저장
            SchoolResponseDto schoolResponseDto = new SchoolResponseDto (
                    schoolEntity.getId(),
                    schoolEntity.getSchoolName(),
                    schoolMajor.stream().map(it -> {
                        MajorEntity major = it.getMajorEntity();
                        return new MajorResponseDto(
                                major.getId(),
                                major.getMajorName(),
                                major.getMajorProfessor()
                        );
                    }
                    ).toList()

            );

            //userResponseDto 선언 후 데이터 저장
            UserResponseDto userResponseDto = new UserResponseDto(
                    userEntity.getId(),
                    userEntity.getName(),
                    userEntity.getGender(),
                    userEntity.getAge(),
                    schoolResponseDto
            );
            dtos.add(userResponseDto);
        }
        //반환
        return dtos;
    }

    @Transactional(readOnly = true)
    public UserResponseDto getById(Long id) {

        //userEntity 에 user데이터 담기 id가 다를 경우 경고 문구
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 UserId입니다.")
        );

        //schoolEntity 선언 userEntity의 getSchoolEntity 데이터를 가져온다
        SchoolEntity schoolEntity = userEntity.getSchoolEntity();
        //schoolResponseDto선언해 반환 할때 함께 반환한다.
        List<SchoolMajor> schoolMajor = schoolEntity.getSchoolMajors();

        // schoolEntity 를 schoolResponseDto 에 담기
        SchoolResponseDto schoolResponseDto = new SchoolResponseDto (
                schoolEntity.getId(),
                schoolEntity.getSchoolName(),
                schoolMajor.stream().map(it -> {
                            MajorEntity major = it.getMajorEntity();
                            return new MajorResponseDto(
                                    major.getId(),
                                    major.getMajorName(),
                                    major.getMajorProfessor()
                            );
                        }
                ).toList()
        );

        //반환
        return new UserResponseDto(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getGender(),
                userEntity.getAge(),
                schoolResponseDto
        );
    }

    @Transactional
    public UserResponseDto updateId(Long id, UserRequestDto userRequestDto){
        //맞는 아이디를 찾아 user 데이터를 userEntity에 담기 맞지 않을 경우 경고 문구
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 UserId 입니다.")
        );

        //맞는 아이디를 찾아 school의 데이터를 schoolEntity에 담기 맞지 않을 경우 경고 문구
        MajorEntity majorEntity = majorRepository.findById(userRequestDto.getMajorId()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 MajorId 입니다")
        );

        //userEntity 내용 수정
        userEntity.update(
                userRequestDto.getName(),
                userRequestDto.getGender(),
                userRequestDto.getAge(),
                majorEntity
        );

        //SchoolResponsDto 타입의 schoolResponseDto에 id,major,gtrade값이 담긴 SchoolResponseDto 담기
        List<SchoolMajor> schoolMajor = majorEntity.getSchoolMajors();

        // schoolEntity 를 schoolResponseDto 에 담기
        MajorResponseDto majorResponseDto = new MajorResponseDto (
                majorEntity.getId(),
                majorEntity.getSchoolName(),
                schoolMajor.stream().map(it -> {
                            MajorEntity major = it.getMajorEntity();
                            return new MajorResponseDto(
                                    major.getId(),
                                    major.getMajorName(),
                                    major.getMajorProfessor()
                            );
                        }
                ).toList()

        );

        //UserResponseDto 반환
        return new UserResponseDto(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getGender(),
                userEntity.getAge(),
                schoolResponseDto
        );
    }

    @Transactional
    //삭제
    public void deleteId(Long id) {
        //userEntity에 id를 찾아 담는다 없을 경우 경고문구
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 UserId 입니다.")
        );
        userRepository.deleteById(id);
    }
}
    //공개| 반환x|메소드| Man 의 man
//    @Transactional//데이터 안전 보장
//
//    public Man save(Man man) {
//        //dto를 받아서 엔티티로 변환
//        UserEntity manEntity = new UserEntity(
//                man.getName(),//이름
//                man.getGender(),//성별
//                man.getAge()//나이
//        );
//        //db(엔티티)에 저장
//        UserEntity saveManEntity = userRepository.save(manEntity);
//        //저장된 엔티티를 dto로 변환
//        Man result = new Man(
//            manEntity.getId(),
//            manEntity.getName(), //이름
//            manEntity.getGender(), //성별
//            manEntity.getAge(),//나이
//            manEntity.getSchoolEntity().getId(),
//            manEntity.getSchoolEntity().getMajorEntity()
//        );
//        //반환
//        return result;
//    }
    //List for문을 통해 탐색
    //공개 |반환x|메소드명(매개변수 없음)
//    @Transactional(readOnly = true)
//    public List<UserResponseDto> getAll() {
//        //데이터 베이스 에서 엔티티 조회 후 manEntity에 담는다.
//        List<UserResponseDto> manEntities = userRepository.findAll();
//        //반환 리스트 선언
//        List<UserResponseDto> dtos = new ArrayList<>();
//
//        //엔티티를 dto로 변환
//        for(UserEntity manEntity : manEntities) {
//
//            UserResponseDto man = new UserEntity(
//                manEntity.getId(),
//                manEntity.getScoolId(),
//                manEntity.getName(),//이름
//                manEntity.getGender(),//성별
//                manEntity.getAge()
//            );
//            //dtos에 추가
//            dtos.add(man);
//        }
//        return dtos;
//    }
//    public List<UserEntity> getAll() {
//        //타입   |  변수  | = | 객체 생성
//        List<UserEntity> manEntityList = new ArrayList<>();
//
//        //List에 데이터 없을 시 경고
//        if(manEntities.isEmpty()) {
//            throw new IllegalArgumentException("조회 가능한 데이터가 없습니다.");
//        }
        //List의 0번 부터 ~ List내 값의 변수까지
        //for (선언; 조건 ; 연산자;)
//        for(int i = 0; i < manEntities.size(); i++ ){//i는 index의 i/index => 위치번호
//
//            //클래스 | 변수 | 에 저장 | 변수.매소드(변수)
//            UserEntity manEntity = manEntities.get(i);
//            System.out.println(
//                "이름: " + manEntity.getName() +
//                " 성별: " + manEntity.getGender() +
//                " 나이: " + manEntity.getAge()
//            );//출력
//        }
        //List 타입의 manList를 반환
//        int i = 0;
//        while(i < manEntities.size()){
//
//            UserEntity manEntity = manEntities.get(i);
//            System.out.println(
//                    "이름:" + manEntity.getName() +
//                    " 성별: " + manEntity.getGender() +
//                    " 나이: " + manEntity.getAge()
//            );
//            i = i + 1;
//        }
//        int i = 0;
//        do{
//
//            UserEntity manEntity = manEntities.get(i);
//            System.out.println(
//                    "이름: " + manEntity.getName() +
//                    " 성별: " + manEntity.getGender() +
//                    " 나이: " + manEntity.getAge()
//            );
//            i = i + 1;
//        }while(i < manEntities.size());
//단건 조회
//    @Transactional
//    //UserEntity 타입의 manEntity에 데이터베이스에서 특정 아이디와 맞는 데이터 가져오기
//    public UserResponseDto getById(Long manId){
//        UserEntity manEntity = userRepository.findById(manId).orElseThrow(
//                //다를 시 경고문구
//                () -> new IllegalArgumentException("존재하지 않는 Man 입니다.")
//        );
//
//        //Man(dto) 반환한다
//        return new UserResponseDto(
//                manEntity.getId(),
//                manEntity.getSchoolId(),
//                manEntity.getName(),
//                manEntity.getGender(),
//                manEntity.getAge()
//        );
//    }
//
//    @Transactional
//    //공개| 반환 타입| 메소드| index 호출 | man
//    public Man update(Long manId, Man man) {
//        //manId로 조회
//        UserEntity manEntity = userRepository.findById(manId).orElseThrow(
//                () -> new IllegalArgumentException("존재하지 않는 Man 입니다.")
//        );
//
//        if(index < 0 || index >= manEntities.size()){
//           throw new IllegalArgumentException("존재하지 않는 Man 입니다.");
//        }
//        //manEntities에서 가져와서 새로운 ManEntity로 변경
//        //엔티티 데이터 수정
//        manEntity.updateMan(man.getName());
//        //dto변환
//        return new Man(
//            manEntity.getId(),
//            manEntity.getName(),
//            manEntity.getGender(),
//            manEntity.getAge(),
//            manEntity.getSchoolEntity().getId(),
//            manEntity.getSchoolEntity().getMajorEntity()
//        );
//    }
//
//    @Transactional
//    public void delete(Long manId) {
//        //manId로 조회
//        UserEntity manEntity = userRepository.findById(manId).orElseThrow(
//                () -> new IllegalArgumentException("존재하지 않는 Man 입니다.")
//        );
//
//        if(id < 0 || id >= manEntities.size()){
//            throw new IllegalArgumentException("존재하지 않는 Man 입니다.");
//     }
//        manEntities.remove(manEntities.get(id));
//        // man Repository의 manId 삭제
//        userRepository.deleteById(manId);
//    }
//}

