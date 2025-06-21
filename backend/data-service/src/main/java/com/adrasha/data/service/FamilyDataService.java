package com.adrasha.data.service;

import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.data.model.Family;
import com.adrasha.data.model.Member;

public interface FamilyDataService {

    Page<Family> getAllFamilies(Example<Family> example, Pageable pageable);

    long getCount(Example<Family> example);
    
    Family getFamily(UUID familyId) throws NotFoundException;
    
	Family createFamily(Family family, Member headMember) throws AlreadyExistsException;
    
    Family updateFamily(UUID familyId, Family updatedFamilyDetails) throws NotFoundException;
    
    Family deleteFamily(UUID familyId) throws NotFoundException;



}
