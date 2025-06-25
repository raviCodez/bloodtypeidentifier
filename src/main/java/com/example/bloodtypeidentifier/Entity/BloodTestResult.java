package com.example.bloodtypeidentifier.Entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "blood_test_results")
public class BloodTestResult {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String bloodType;
        private String imagePath;
        private LocalDateTime timestamp;
    }

