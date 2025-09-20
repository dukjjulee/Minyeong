package org.example.minyeong.dto;

import lombok.Getter;
import org.example.minyeong.constant.Gender;
import org.example.minyeong.entity.UserEntity;

@Getter
public class UserResponseDto {

    private final Long id;
    private final String name;
    private final Gender gender;
    private final Integer age;
    private final Integer viewCount;

    //연관관계 School의 정보
    private final SchoolResponseDto schoolResponseDto;
    private final MajorResponseDto majorResponseDto;

        public UserResponseDto(
            Long id,
            String name,
            Gender gender,
            Integer age,
            SchoolResponseDto schoolResponseDto,
            MajorResponseDto majorResponseDto,
            Integer viewCount
    ){
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.schoolResponseDto = schoolResponseDto;
        this.majorResponseDto = majorResponseDto;
        this.viewCount = viewCount;
    }

    public static UserResponseDto from(UserEntity userEntity) {
            return new UserResponseDto(
                    userEntity.getId(),
                    userEntity.getName(),
                    userEntity.getGender(),
                    userEntity.getAge(),
                    SchoolResponseDto.from(userEntity.getSchoolEntity()),
                    MajorResponseDto.from(userEntity.getMajorEntity()),
                    userEntity.getViewCount()
            );
    }
}
