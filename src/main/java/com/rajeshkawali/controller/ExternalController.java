package com.rajeshkawali.controller;

import com.rajeshkawali.dto.University;
import com.rajeshkawali.service.ExternalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Rajesh_Kawali
 */
@Slf4j
@RequestMapping("/api")
@RestController
public class ExternalController {

    public static final String CLASS_NAME = ExternalController.class.getName();
    String methodName = "";

    @Autowired
    private ExternalService externalService;

    @GetMapping("/v1/universities/{country}")
    public List<University> getUniversitiesByCountry(@PathVariable String country) {
        methodName = ".getUniversitiesByCountry";
        log.info(CLASS_NAME + methodName + "::ENTER");
        return externalService.getUniversitiesByCountry(country);
    }
}