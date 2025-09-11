package org.example.minyeong.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class MajorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String majorName;
    private String majorProfessor;

    @OneToMany(mappedBy = "majorEntity")
    private List<SchoolMajor> schoolMajors = new ArrayList<>();

    @OneToMany(mappedBy = "majorEntity")
    private List<UserEntity> userEntity = new ArrayList<>();;

    public MajorEntity(
            String majorName,
            String majorProfessor
    ){
        this.majorName = majorName;
        this.majorProfessor = majorProfessor;
    }

    public void updateMajor(
            String majorName,
            String majorProfessor
    ){
        this.majorName = majorName;
        this.majorProfessor = majorProfessor;
    }
}
