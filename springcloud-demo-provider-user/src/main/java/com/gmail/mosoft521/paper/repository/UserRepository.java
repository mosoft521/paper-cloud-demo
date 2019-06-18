package com.gmail.mosoft521.paper.repository;

import com.gmail.mosoft521.paper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
