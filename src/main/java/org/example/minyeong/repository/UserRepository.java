package org.example.minyeong.repository;

import org.example.minyeong.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Page<UserEntity> findAllByNicknameLike(String nickname, Pageable pageable);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByPassword(String password);
}
