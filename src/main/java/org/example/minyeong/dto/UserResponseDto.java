package org.example.minyeong.dto;

import lombok.Getter;

@Getter
public class UserResponseDto {

    private final Long id;
    private final String name;
    private final String gender;
    private final Integer age;

    //연관관계 School의 정보
    private final MajorResponseDto majorResponseDto;

        public UserResponseDto(
            Long id,
            String name,
            String gender,
            Integer age,
            MajorResponseDto majorResponseDto
    ){
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.majorResponseDto = majorResponseDto;
    }
}
