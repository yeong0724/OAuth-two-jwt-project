package com.jinyeong.oauthtwojwt.repository;

import com.jinyeong.oauthtwojwt.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}