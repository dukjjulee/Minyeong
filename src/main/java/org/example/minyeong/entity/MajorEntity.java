package org.example.minyeong.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
