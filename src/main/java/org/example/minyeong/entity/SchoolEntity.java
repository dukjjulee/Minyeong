package org.example.minyeong.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class SchoolEntity {

    //id는 자동 생성, 유일함
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String schoolName;

    @OneToMany(mappedBy = "schoolEntity")
    private List<UserEntity> userEntity = new ArrayList<>();;

    @OneToMany(mappedBy = "schoolEntity")
    private List<SchoolMajor> schoolMajors = new ArrayList<>();

    public SchoolEntity(String schoolName){
        this.schoolName = schoolName;
    }

    public void updateSchool(String grade) {
        this.schoolName = grade;
    }
}
