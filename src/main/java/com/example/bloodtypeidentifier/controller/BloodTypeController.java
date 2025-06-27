package com.example.bloodtypeidentifier.controller;

import com.example.bloodtypeidentifier.entity.BloodTestResult;
import com.example.bloodtypeidentifier.service.BloodTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BloodTypeController {
    @Autowired
    private BloodTypeService bloodTypeService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/identify")
    public String identify(@RequestParam("image") MultipartFile image, Model model) {
        try {
            BloodTestResult result = bloodTypeService.determineBloodTypeFromImage(image);
            model.addAttribute("bloodType", result.getBloodType());
            model.addAttribute("imagePath", result.getImagePath());
            return "result";
        } catch (Exception e) {
            model.addAttribute("error", "Error processing image: " + e.getMessage());
            return "index";
        }
    }
}