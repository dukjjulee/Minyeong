package org.example.minyeong.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SchoolEntity extends BaseEntity {

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

    public void update(String schoolName) {
        this.schoolName = schoolName;
    }

    public static SchoolEntity create(String schoolName) {
        return new SchoolEntity(schoolName);
    }
}
