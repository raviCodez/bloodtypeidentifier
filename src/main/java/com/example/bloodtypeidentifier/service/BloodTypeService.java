package com.example.bloodtypeidentifier.service;

import com.example.bloodtypeidentifier.entity.BloodTestResult;
import com.example.bloodtypeidentifier.repository.BloodTestResultRepository;
import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.preprocessor.VGG16ImagePreProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class BloodTypeService {
    @Autowired
    private BloodTestResultRepository resultRepository;
    private ComputationGraph model;

    @PostConstruct
    public void init() throws Exception {
        // Load pre-trained ResNet model from Keras .h5 file
        String modelPath = "src/main/resources/models/model_blood_group_detection_resnet.h5";
        model = KerasModelImport.importKerasModelAndWeights(modelPath, false);
    }

    public BloodTestResult determineBloodTypeFromImage(MultipartFile image) throws IOException {
        // Save image
        String uploadDir = "uploads/";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        String filePath = uploadDir + fileName;
        image.transferTo(new File(filePath));

        // Preprocess image
        NativeImageLoader loader = new NativeImageLoader(224, 224, 3);
        INDArray input = loader.asMatrix(new File(filePath));
        VGG16ImagePreProcessor preProcessor = new VGG16ImagePreProcessor();
        preProcessor.transform(input);

        // Predict blood type
        INDArray output = model.outputSingle(input);
        int classIndex = output.argMax(1).getInt(0);
        String[] bloodTypes = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        String bloodType = bloodTypes[classIndex];

        // Save result
        BloodTestResult result = new BloodTestResult();
        result.setBloodType(bloodType);
        result.setImagePath(filePath);
        result.setTimestamp(LocalDateTime.now());
        resultRepository.save(result);

        return result;
    }
}
