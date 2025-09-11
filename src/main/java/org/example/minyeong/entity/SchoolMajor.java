package org.example.minyeong.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class SchoolMajor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private SchoolEntity schoolEntity;

    @ManyToOne
    @JoinColumn(name = "major_id")
    private MajorEntity majorEntity;

    public SchoolMajor(SchoolEntity schoolEntity, MajorEntity majorEntity){
        this.schoolEntity = schoolEntity;
        this.majorEntity = majorEntity;
    }
}
