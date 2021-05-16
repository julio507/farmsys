package com.julio.farmsys.repository;


import java.util.List;
import java.util.Optional;

import com.julio.farmsys.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query( "SELECT u FROM User u WHERE u.name LIKE %?1% AND u.email LIKE %?2% AND enabled = ?3" )
    List<User> find( String name, String email, boolean active );
}
