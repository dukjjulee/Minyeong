package org.example.minyeong;
import org.example.minyeong.constant.Gender;
import org.example.minyeong.dto.UserRequestDto;
import org.example.minyeong.dto.UserResponseDto;
import org.example.minyeong.entity.MajorEntity;
import org.example.minyeong.entity.SchoolEntity;
import org.example.minyeong.entity.UserEntity;
import org.example.minyeong.repository.MajorRepository;
import org.example.minyeong.repository.SchoolRepository;
import org.example.minyeong.repository.UserRepository;
import org.example.minyeong.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SchoolRepository schoolRepository;

    @Mock
    private MajorRepository majorRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void User를_생성() {
        //given
        Long id = 1L;
        String name = "강민영";
        Gender gender = Gender.male;
        Integer age = 23;
        String email = "minyeong1234";
        String password = "minmin1234";

        SchoolEntity school = new SchoolEntity("학교명");
        MajorEntity major = new MajorEntity("전공", "교수");

        UserEntity user = UserEntity.create(name, gender, age, email, password, school, major);
        ReflectionTestUtils.setField(user,"id", id);

        given(userRepository.save(any(UserEntity.class))).willReturn(user);
        given(schoolRepository.findById(1L)).willReturn(Optional.of(school));
        given(majorRepository.findById(1L)).willReturn(Optional.of(major));

        UserRequestDto userRequestDto = new UserRequestDto(
                1L,
                1L,
                "강민영",
                Gender.male,
                20,
                "test.test.me",
                "password");

        //when
        UserResponseDto userResponseDto = userService.save(userRequestDto);

        //then
        assertThat(userResponseDto).isNotNull();
        assertThat(userResponseDto.getId()).isEqualTo(id);
        assertThat(userResponseDto.getName()).isEqualTo(name);
        assertThat(userResponseDto.getGender()).isEqualTo(gender);
        assertThat(userResponseDto.getAge()).isEqualTo(age);
        assertThat(userResponseDto.getSchoolResponseDto().getSchoolName()).isEqualTo(school.getSchoolName());
        assertThat(userResponseDto.getMajorResponseDto().getMajorName()).isEqualTo(major.getMajorName());
        assertThat(userResponseDto.getMajorResponseDto().getMajorProfessor()).isEqualTo(major.getMajorProfessor());
    }

    @Test
    void user_를_ID로_조회할_수_있다() {
        //given
        Long id = 1L;
        String name = "강민영";
        Gender gender = Gender.male;
        Integer age = 23;
        String email = "minyeong1234";
        String password = "minmin1234";
        SchoolEntity school = new SchoolEntity("학교명");
        MajorEntity major = new MajorEntity("전공", "교수");

        UserEntity user = new UserEntity(name, gender, age, email, password, school, major);

        ReflectionTestUtils.setField(user,"id", id);

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));

        //when
        UserResponseDto userResponseDto = userService.getById(id);

        //then
        assertThat(userResponseDto).isNotNull();
        assertThat(userResponseDto.getId()).isEqualTo(id);
        assertThat(userResponseDto.getName()).isEqualTo(name);
        assertThat(userResponseDto.getGender()).isEqualTo(gender);
        assertThat(userResponseDto.getAge()).isEqualTo(age);
        assertThat(userResponseDto.getSchoolResponseDto().getSchoolName()).isEqualTo(school.getSchoolName());
        assertThat(userResponseDto.getMajorResponseDto().getMajorName()).isEqualTo(major.getMajorName());
        assertThat(userResponseDto.getMajorResponseDto().getMajorProfessor()).isEqualTo(major.getMajorProfessor());
    }

    @Test
    void 존재하지_않는_User를_조회_시_IllegalArgumentException을_던진다(){
        //geven
        Long id = 1L;
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        //when & then
        assertThrows(IllegalArgumentException.class, () -> userService.getById(id), "User not found");
    }

}
