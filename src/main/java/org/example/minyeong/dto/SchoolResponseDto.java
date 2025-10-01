package org.example.minyeong.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.minyeong.entity.SchoolEntity;

@Getter
@NoArgsConstructor
public class SchoolResponseDto {
    private Long id;
    private String schoolName;

    public SchoolResponseDto(
            Long id,
            String schoolName
    ){
        this.id = id;
        this.schoolName = schoolName;
    }

    public static SchoolResponseDto from(SchoolEntity schoolEntity) {
        return new SchoolResponseDto(schoolEntity.getId(), schoolEntity.getSchoolName());
    }
}
