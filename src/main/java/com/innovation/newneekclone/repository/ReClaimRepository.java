package com.innovation.newneekclone.repository;

import com.innovation.newneekclone.entity.Claim;
import com.innovation.newneekclone.entity.ReClaim;
import com.innovation.newneekclone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReClaimRepository extends JpaRepository<ReClaim, Long> {

    Optional<ReClaim> findByUser(User user);
    List<ReClaim> findAll();
    Optional<ReClaim> findByClaim(Claim claim);
}