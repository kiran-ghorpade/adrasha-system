package com.adrasha.family.service;

import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.family.model.Family;

public interface FamilyService {

    Page<Family> getAllFamilies(Example<Family> example, Pageable pageable);
    
    Family getFamily(UUID familyId) throws NotFoundException;
    
    Family createFamily(Family family) throws AlreadyExistsException;
    
    Family updateFamily(UUID familyId, Family updatedFamilyDetails) throws NotFoundException;
    
    Family deleteFamily(UUID familyId) throws NotFoundException;
}
