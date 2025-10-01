package org.example.minyeong.dto;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.example.minyeong.constant.Gender;

@Setter
@Getter
public class UserRequestDto {

    private Long id;
    private Long schoolId;
    private Long majorId;

    @NotBlank(message = "name 은 비워둘 수 없습니다.")
    @Size(min = 3, max = 10, message = "name은 3 ~ 10자 입니다.")
    private String name;

    @Enumerated
    private Gender gender;

    @Max(value = 120, message = "120자를 넘을 수 없습니다.")
    private Integer age;

    @NotBlank(message = "email 은 비워둘 수 없습니다.")
    @Size(min = 3, max = 64, message = "email은 3 ~ 64자 입니다.")
    private String email;

    @NotBlank(message = "password 은 비워둘 수 없습니다.")
    @Size(min = 8, max = 64, message = "password는 8 ~ 64자 입니다.")
    private String password;

    public UserRequestDto(
            Long schoolId,
            Long majorId,
            String name,
            Gender gender,
            Integer age,
            String email,
            String password
    ) {
        this.schoolId = schoolId;
        this.majorId = majorId;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.email = email;
        this.password = password;
    }
}
