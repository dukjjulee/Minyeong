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
    private Integer grade;

    @OneToMany(mappedBy = "school")
    private List<UserEntity> users;

    @OneToMany
    private List<SchoolMajor> schoolMajors;

    public SchoolEntity(Integer grade){
        this.grade = grade;
    }

    public void updateSchool(Integer grade) {
        this.grade = grade;
    }
}
