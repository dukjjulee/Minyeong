package org.example.minyeong.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
public class SchoolResponseDto {
    private Long id;
    private Integer grade;

    private List<MajorResponseDto> majors;

    public SchoolResponseDto(
            Long id,
            Integer grade,
            List<MajorResponseDto> majors
    ){
        this.id = id;
        this.grade = grade;
        this.majors = majors;
    }
}
