package com.project.videodemo.key;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface KeyRepository extends JpaRepository<Key, Integer> {

    @Query("select k from Key k where k.userId = :userId")
    Optional<Key> findByUserId(@Param("userId")Integer userId);
}
