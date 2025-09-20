package org.example.minyeong.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class MajorRequestDto {

    @NotBlank(message = "MajorName은 비워둘 수 없습니다.")
    @Size(min = 3, max = 20, message = "MajorName은 3 ~ 20자 입니다.")
    private String majorName;
    @Size(min = 2, max = 10, message = "MajorProfessor은 2 ~ 10자 입니다.")
    private String majorProfessor;
}
