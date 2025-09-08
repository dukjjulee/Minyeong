package org.example.minyeong.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class SchoolResponseDto {
    private Long id;
    private String major;
    private Integer grade;

    public SchoolResponseDto(Long id, String major, Integer grade){
        this.id = id;
        this.major = major;
        this.grade = grade;
    }

    public Long getId(){
        return id;
    }
    public String getMajor(){
        return major;
    }
    public Integer getGrade(){
        return grade;
    }
}
