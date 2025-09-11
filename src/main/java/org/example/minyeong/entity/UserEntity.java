package org.example.minyeong.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)// 필수.외부에서의 직접생성 방지 접근제어자가 PROTECTED로 바뀜
public class UserEntity {//클래스 ManEntity를 선언

    //속성
    //|비공| 타입 | 변수명 |
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer age; //나이
    private String name; //이름
    private String gender; //성별

    //School : Man | 다:1 /UserEntity 필요할 때만, school이 null일 수 없음
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "school_id")//Man테이블에 컬럼 생성
    private SchoolEntity schoolEntity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "major_id")
    private MajorEntity majorEntity;

    public UserEntity(String name, String gender, Integer age, MajorEntity MajorEntity) {
        //ManaEntity 안에 선언된 변수(name)에 매개변수(String name)가 저장된다
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.majorEntity = MajorEntity;
    }

    public void update(String name, String gender, Integer age, MajorEntity MajorEntity) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.majorEntity = MajorEntity;
    }
}
