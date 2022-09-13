package com.innovation.newneekclone.repository;

import com.innovation.newneekclone.entity.Claim;

import com.innovation.newneekclone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ClaimRepository extends JpaRepository<Claim, Long> {

    List<Claim> findByUser(User user);
    List<Claim> findAll();
}