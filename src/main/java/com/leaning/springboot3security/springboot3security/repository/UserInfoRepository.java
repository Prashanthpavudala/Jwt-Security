package com.leaning.springboot3security.springboot3security.repository;

import com.leaning.springboot3security.springboot3security.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    UserInfo findByEmailAndIsActive(String email, Boolean isActive);
}
