package com.adrasha.monolithic.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.monolithic.exception.AshaNotFoundException;
import com.adrasha.monolithic.model.Asha;

public interface AshaService {

	   Page<Asha> getAllAsha(Pageable pageable);
	    
		Asha getAsha(UUID ashaId) throws AshaNotFoundException;
	    
	    Asha createAsha(Asha asha) throws AshaNotFoundException;
	    
	    Asha updateAsha(UUID ashaId, Asha updatedAshaDetails) throws AshaNotFoundException;
	    
	    Asha deleteAsha(UUID ashaId) throws AshaNotFoundException;
}
