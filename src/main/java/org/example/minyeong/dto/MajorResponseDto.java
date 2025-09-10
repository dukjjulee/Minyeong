package org.example.minyeong.dto;

import lombok.Getter;
import org.example.minyeong.entity.MajorEntity;
import org.example.minyeong.repository.MajorRepository;

@Getter
public class MajorResponseDto {
    private final Long id;
    private final String majorName;
    private final String majorUser;
    private final String majorProfessor;

    public MajorResponseDto(
            Long id,
            String majorName,
            String majorUser,
            String majorProfessor
    ){
        this.id = id;
        this.majorName = majorName;
        this.majorUser = majorUser;
        this.majorProfessor = majorProfessor;
    }
}
