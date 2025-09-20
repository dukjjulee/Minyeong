package org.example.minyeong.dto;

import lombok.Getter;
import org.example.minyeong.entity.MajorEntity;

@Getter
public class MajorResponseDto {
    private final Long id;
    private final String majorName;
    private final String majorProfessor;

    public MajorResponseDto(
            Long id,
            String majorName,
            String majorProfessor
    ){
        this.id = id;
        this.majorName = majorName;
        this.majorProfessor = majorProfessor;
    }
    public static MajorResponseDto from(MajorEntity majorEntity) {
        return new MajorResponseDto(majorEntity.getId(), majorEntity.getMajorName(), majorEntity.getMajorProfessor());
    }
}
