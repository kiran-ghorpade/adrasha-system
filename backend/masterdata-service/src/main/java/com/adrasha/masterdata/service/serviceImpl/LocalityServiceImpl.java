package com.adrasha.masterdata.service.serviceImpl;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.masterdata.model.Locality;
import com.adrasha.masterdata.repository.LocalityRepository;
import com.adrasha.masterdata.service.LocalityService;

// TODO : Review class 
@Service
public class LocalityServiceImpl implements  LocalityService{

	@Autowired
	private LocalityRepository  localityRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Page<Locality> getAllLocalities(Pageable pageable, UUID countryId) {

		return localityRepository.findAll(pageable);
	}

	@Override
	public Locality getLocality(UUID localityId) {

		return localityRepository.findById(localityId)
				.orElseThrow(() -> new NotFoundException("Locality Not Found with id : " + localityId));
	}

	@Override
	public Locality createLocality(Locality locality) {
		
		
		Optional<Locality> existingRequest = localityRepository.findByName(locality.getName());
		
	  	if(existingRequest.isPresent()) {
	  		throw new AlreadyExistsException("Locality with name : "+ locality.getName()+" already exist.");
	  	}

		return localityRepository.save(locality);
	}

	@Override
	public Locality updateLocality(UUID localityId, Locality updatedLocality) {
		Locality locality = getLocality(localityId);
		modelMapper.map(updatedLocality, locality);
		return localityRepository.save(locality);
	}

	@Override
	public void deleteLocality(UUID localityId) {
		Locality locality = getLocality(localityId);
		localityRepository.delete(locality);
	}

}
