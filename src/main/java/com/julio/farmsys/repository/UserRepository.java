package com.julio.farmsys.repository;

import com.julio.farmsys.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
