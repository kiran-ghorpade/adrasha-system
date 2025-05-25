package com.adrasha.familyservice.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.familyservice.exception.FamilyNotFoundException;
import com.adrasha.familyservice.model.Family;

public interface FamilyService {

    Page<Family> getAllFamilies(Pageable pageable);
    
    Family getFamily(UUID familyId) throws FamilyNotFoundException;
    
    Family createFamily(Family family) throws FamilyNotFoundException;
    
    Family updateFamily(UUID familyId, Family updatedFamilyDetails) throws FamilyNotFoundException;
    
    Family deleteFamily(UUID familyId) throws FamilyNotFoundException;
}
