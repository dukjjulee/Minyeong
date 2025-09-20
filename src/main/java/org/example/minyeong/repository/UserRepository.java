package org.example.minyeong.repository;

import org.example.minyeong.entity.UserEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Page<UserEntity> findAllByNicknameLike(String nickname, Pageable pageable);
}
