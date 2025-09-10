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
    @JoinColumn(name = "school")
    private SchoolEntity schoolEntity;

    @ManyToOne
    @JoinColumn(name = "major")
    private MajorEntity majorEntity;
}
