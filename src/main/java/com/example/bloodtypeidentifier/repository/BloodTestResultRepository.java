package com.example.bloodtypeidentifier.repository;

import com.example.bloodtypeidentifier.entity.BloodTestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodTestResultRepository extends JpaRepository<BloodTestResult, Long> {
}
