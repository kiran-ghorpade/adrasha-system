package com.adrasha.ashaservice.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.ashaservice.exception.AshaAlreadyExistsException;
import com.adrasha.ashaservice.exception.AshaNotFoundException;
import com.adrasha.ashaservice.model.Asha;

public interface AshaService {

	Page<Asha> getAllAsha(Pageable pageable);
    
    Asha getAsha(UUID ashaId) throws AshaNotFoundException;
    
    Asha createAsha(Asha asha) throws AshaAlreadyExistsException;
    
    Asha updateAsha(UUID ashaId, Asha updatedAshaDetails) throws AshaNotFoundException;
    
    Asha deleteAsha(UUID ashaId) throws AshaNotFoundException;
}
