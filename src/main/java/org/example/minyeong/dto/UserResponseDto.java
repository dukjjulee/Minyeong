package org.example.minyeong.dto;

import lombok.Getter;

@Getter
public class UserResponseDto {

    private final Long id;
    private final String name;
    private final String gender;
    private final Integer age;

    //연관관계 School의 정보
    private final SchoolResponseDto schoolResponseDto;

        public UserResponseDto(
            Long id,
            String name,
            String gender,
            Integer age,
            SchoolResponseDto schoolResponseDto
    ){
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.schoolResponseDto = schoolResponseDto;
    }
}
