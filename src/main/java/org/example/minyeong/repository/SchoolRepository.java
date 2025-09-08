package org.example.minyeong.repository;

import org.example.minyeong.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
//SchoolEntity 와 Long 를 데이터베이스에 저장할것임
public interface SchoolRepository extends JpaRepository<SchoolEntity, Long> {
    Iterable<Long> id(Long id);
}
