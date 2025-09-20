package org.example.minyeong.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SchoolRequestDto {
    private Long id;

    @NotBlank(message = "SchoolName 은 비워둘 수 없습니다.")
    @Size(min = 3, max = 20, message = "SchoolName 는 3 ~ 20자 입니다.")
    private String schoolName;
}
