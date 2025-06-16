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
import com.adrasha.masterdata.model.Subdistrict;
import com.adrasha.masterdata.repository.SubdistrictRepository;
import com.adrasha.masterdata.service.SubdistrictService;

// TODO : Review class 
@Service
public class SubdistrictServiceImpl implements  SubdistrictService{

	@Autowired
	private SubdistrictRepository  subdistrictRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Page<Subdistrict> getAllSubdistricts(Pageable pageable, UUID countryId) {

		return subdistrictRepository.findAll(pageable);
	}

	@Override
	public Subdistrict getSubdistrict(UUID subdistrictId) {

		return subdistrictRepository.findById(subdistrictId)
				.orElseThrow(() -> new NotFoundException("Subdistrict Not Found with id : " + subdistrictId));
	}

	@Override
	public Subdistrict createSubdistrict(Subdistrict subdistrict) {
		
		
		Optional<Subdistrict> existingRequest = subdistrictRepository.findByName(subdistrict.getName());
		
	  	if(existingRequest.isPresent()) {
	  		throw new AlreadyExistsException("Subdistrict with name : "+ subdistrict.getName()+" already exist.");
	  	}

		return subdistrictRepository.save(subdistrict);
	}

	@Override
	public Subdistrict updateSubdistrict(UUID subdistrictId, Subdistrict updatedSubdistrict) {
		Subdistrict subdistrict = getSubdistrict(subdistrictId);
		modelMapper.map(updatedSubdistrict, subdistrict);
		return subdistrictRepository.save(subdistrict);
	}

	@Override
	public void deleteSubdistrict(UUID subdistrictId) {
		Subdistrict subdistrict = getSubdistrict(subdistrictId);
		subdistrictRepository.delete(subdistrict);
	}

}
