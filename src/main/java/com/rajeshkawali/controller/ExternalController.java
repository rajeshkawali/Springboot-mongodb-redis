package com.rajeshkawali.controller;

import com.rajeshkawali.dto.University;
import com.rajeshkawali.service.ExternalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private ExternalService externalService;

    /**
     * Get list of universities by country.
     *
     * @param country - Country name as path variable.
     * @return List of universities in the specified country.
     */
    @GetMapping("/v1/universities/{country}")
    public ResponseEntity<List<University>> getUniversitiesByCountry(@PathVariable String country) {
        try {
            // Call the external service to fetch universities by country.
            List<University> universities = externalService.getUniversitiesByCountry(country);
            // Return the list of universities with a 200 OK response.
            return new ResponseEntity<>(universities, HttpStatus.OK);
        } catch (Exception e) {
            // Log the error and return a proper response.
            log.error("ERROR - Failed to retrieve universities for country: {}. Error: {}", country, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            // Optional: Log that the method execution is complete.
            log.info("EXIT");
        }
    }
}