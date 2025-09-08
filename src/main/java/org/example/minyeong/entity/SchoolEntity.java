package org.example.minyeong.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class SchoolEntity {

    //id는 자동 생성, 유일함
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String major;
    private Integer grade;


    //School : Man | 다:1
    @OneToMany(mappedBy = "school")
    //School안에 여러 Man
    private List<UserEntity> men ;

    public SchoolEntity(String major, Integer grade){
        this.major = major;
        this.grade = grade;
    }

    public void updateSchool(String major, Integer grade) {
        this.major = major;
        this.grade = grade;
    }
}
