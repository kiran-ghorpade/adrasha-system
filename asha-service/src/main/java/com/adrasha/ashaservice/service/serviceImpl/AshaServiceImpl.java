package com.adrasha.ashaservice.service.serviceImpl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adrasha.ashaservice.exception.AshaAlreadyExistsException;
import com.adrasha.ashaservice.exception.AshaNotFoundException;
import com.adrasha.ashaservice.model.Asha;
import com.adrasha.ashaservice.repository.AshaRepository;
import com.adrasha.ashaservice.service.AshaService;

@Service
public class AshaServiceImpl implements AshaService {

	@Autowired
	private AshaRepository ashaRepository;

	@Override
	public Page<Asha> getAllAsha(Pageable pageable) {

		return ashaRepository.findAll(pageable);
	}

	@Override
	public Asha getAsha(UUID ashaId) {
		
		return ashaRepository.findById(ashaId)
				.orElseThrow(() -> new AshaNotFoundException("Asha Not Found with id : " + ashaId));
	}

	@Override
	public Asha createAsha(Asha asha) {
	  	if(ashaRepository.findByMobileNumber(asha.getMobileNumber()).isPresent()) {
	  		throw new AshaAlreadyExistsException("Asha with mobileNumber : "+ asha.getMobileNumber()+" already exist.");
	  	}

		return ashaRepository.save(asha);
	}

	@Override
	public Asha updateAsha(UUID ashaId, Asha updatedAshaDetails) {

		return ashaRepository.save(updatedAshaDetails);
	}

	@Override
	public Asha deleteAsha(UUID ashaId) {
		Asha asha = getAsha(ashaId);
		ashaRepository.deleteById(ashaId);
		return asha;
	}

}
