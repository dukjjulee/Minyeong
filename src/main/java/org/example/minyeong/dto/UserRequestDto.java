package org.example.minyeong.dto;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.example.minyeong.constant.Gender;

@Getter
public class UserRequestDto {

    private Long id;
    private Long schoolId;
    private Long majorId;

    @NotBlank(message = "UserName 은 비워둘 수 없습니다.")
    @Size(min = 3, max = 10, message = "UserName은 3 ~ 10자 입니다.")
    private String name;

    @Enumerated
    private Gender gender;

    @Max(value = 120, message = "120자를 넘을 수 없습니다.")
    private Integer age;
}
