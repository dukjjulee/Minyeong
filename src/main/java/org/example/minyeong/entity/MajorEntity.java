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
    private String majorUser;
    private String majorProfessor;

    @OneToMany(mappedBy = "major")
    private List<SchoolMajor> schoolMajors = new ArrayList<>();

    @OneToMany(mappedBy = "major")
    private List<UserEntity> users;

    public MajorEntity(
            String majorName,
            String majorUser,
            String majorProfessor
    ){
        this.majorName = majorName;
        this.majorUser = majorUser;
        this.majorProfessor = majorProfessor;
    }

    public void updateMajor(
            String majorName,
            String majorUser,
            String majorProfessor
    ){
        this.majorName = majorName;
        this.majorUser = majorUser;
        this.majorProfessor = majorProfessor;
    }
}
