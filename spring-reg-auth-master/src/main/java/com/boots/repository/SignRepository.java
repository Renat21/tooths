package com.boots.repository;

import com.boots.entity.Sign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignRepository extends JpaRepository<Sign, Long> {

    Sign findSignByDoctor(String doctor);

    Sign findSignById(Long id);
}