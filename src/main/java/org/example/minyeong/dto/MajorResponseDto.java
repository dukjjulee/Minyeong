package org.example.minyeong.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class MajorResponseDto {
    private final Long id;
    private final String majorName;
    private final String majorProfessor;
    private SchoolResponseDto schoolResponseDto;

    public MajorResponseDto(
            Long id,
            String majorName,
            String majorProfessor,
            SchoolResponseDto schoolResponseDo
    ){
        this.id = id;
        this.majorName = majorName;
        this.majorProfessor = majorProfessor;
        this.schoolResponseDto = schoolResponseDto;
    }
}
