package com.rajeshkawali.service;

import com.rajeshkawali.dto.University;

import java.util.List;
/**
 * @author Rajesh_Kawali
 */
public interface ExternalService {
    public List<University> getUniversitiesByCountry(String country);
}
