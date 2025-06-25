package com.example.bloodtypeidentifier.Repository;

import com.example.bloodtypeidentifier.Entity.BloodTestResult;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BLoodTestResultRepository extends  JpaRepository<BloodTestResult,Long> {
}
