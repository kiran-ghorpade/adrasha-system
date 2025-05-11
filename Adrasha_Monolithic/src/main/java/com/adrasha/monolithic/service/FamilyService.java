package com.adrasha.monolithic.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.monolithic.exception.FamilyNotFoundException;
import com.adrasha.monolithic.model.Family;

public interface FamilyService {

    Page<Family> getAllFamilies(Pageable pageable);
    
    Family getFamily(UUID familyId) throws FamilyNotFoundException;
    
    Family createFamily(Family family) throws FamilyNotFoundException;
    
    Family updateFamily(UUID familyId, Family updatedFamilyDetails) throws FamilyNotFoundException;
    
    Family deleteFamily(UUID familyId) throws FamilyNotFoundException;
}
