package org.example.minyeong;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.minyeong.constant.Gender;
import org.example.minyeong.dto.UserRequestDto;
import org.example.minyeong.entity.MajorEntity;
import org.example.minyeong.entity.SchoolEntity;
import org.example.minyeong.entity.UserEntity;
import org.example.minyeong.repository.MajorRepository;
import org.example.minyeong.repository.SchoolRepository;
import org.example.minyeong.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private MajorRepository majorRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void saveUser() throws Exception{
        //given
        UserRequestDto requestDto = new UserRequestDto(
                1L,
                1L,
                "강민영",
                Gender.male,
                20,
                "test.test.me",
                "password"
        );

        String jsonRequestDto = objectMapper.writeValueAsString(requestDto);

        //when
        ResultActions perform = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequestDto));

        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(requestDto.getName()))
                .andExpect(jsonPath("$.gender").value(requestDto.getGender().toString()))
                .andExpect(jsonPath("$.age").value(requestDto.getAge()))
                .andExpect(jsonPath("$.schoolResponseDto.id").value(requestDto.getSchoolId()))
                .andExpect(jsonPath("$.schoolResponseDto.schoolName").isNotEmpty())
                .andExpect(jsonPath("$.majorResponseDto.id").value(requestDto.getMajorId()))
                .andExpect(jsonPath("$.majorResponseDto.majorName").isNotEmpty());

        MvcResult mvcResult = perform.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();

        UserEntity user = userRepository.findById(id).orElseThrow();
        assertThat(user.getName()).isEqualTo("강민영");
        assertThat(user.getGender()).isEqualTo(Gender.male);
        assertThat(user.getAge()).isEqualTo(20);
        assertThat(user.getEmail()).isEqualTo("test.test.me");
        assertThat(user.getSchoolEntity().getSchoolName()).isEqualTo("고양이 학교");
        assertThat(user.getMajorEntity().getMajorName()).isEqualTo("고전미술");
        assertThat(user.getMajorEntity().getMajorProfessor()).isEqualTo("레오나르도");
    }

    @Test
    void findAllUser() throws Exception {

        SchoolEntity school = schoolRepository.save(new SchoolEntity("학교명"));
        MajorEntity major = majorRepository.save(new MajorEntity("전공", "교수"));

        userRepository.save(new UserEntity(
                "name1",
                Gender.male,
                20,
                "test1.test.me",
                "password1",
                school,
                major
                )
        );

        userRepository.save(new UserEntity(
                "name2",
                Gender.male,
                20,
                "test2.test.me",
                "password2",
                school,
                major
                )
        );

        userRepository.save(new UserEntity(
                "name3",
                Gender.female,
                20,
                "test3.test.me",
                "password3",
                school,
                major
                )
        );

        assertThat(userRepository.count()).isEqualTo(6);

        ResultActions perform = mockMvc.perform(get("/users"));
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(0));
    }

    @Test
    void findByUser() throws Exception {

        SchoolEntity school = schoolRepository.save(new SchoolEntity("학교명"));
        MajorEntity major = majorRepository.save(new MajorEntity("전공", "교수"));

        UserEntity user = userRepository.save(new UserEntity(
                "name1",
                Gender.male,
                20,
                "test1.test.me",
                "password1",
                school,
                major
                )
        );

        assertThat(userRepository.count()).isEqualTo(1);

        ResultActions perform = mockMvc.perform(get("/users/" + user.getId()));

        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("name1"))
                .andExpect(jsonPath("$.gender").value("male"));
    }

    @GetMapping("test")
    public String test() {
        return "로그인 유저";
    }
}
