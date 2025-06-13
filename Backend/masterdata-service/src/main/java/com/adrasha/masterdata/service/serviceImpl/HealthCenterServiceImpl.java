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
import com.adrasha.masterdata.model.HealthCenter;
import com.adrasha.masterdata.repository.HealthCenterRepository;
import com.adrasha.masterdata.service.HealthCenterService;

// TODO : Review class 
@Service
public class HealthCenterServiceImpl implements HealthCenterService {

	@Autowired
	private HealthCenterRepository  healthCenterRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Page<HealthCenter> getAllHealthCenters(Pageable pageable) {

		return healthCenterRepository.findAll(pageable);
	}

	@Override
	public HealthCenter getHealthCenter(UUID healthCenterId) {

		return healthCenterRepository.findById(healthCenterId)
				.orElseThrow(() -> new NotFoundException("HealthCenter Not Found with id : " + healthCenterId));
	}

	@Override
	public HealthCenter createHealthCenter(HealthCenter healthCenter) {
		
		Optional<HealthCenter> existingRequest = healthCenterRepository.findByName(healthCenter.getName());
		
	  	if(existingRequest.isPresent()) {
	  		throw new AlreadyExistsException("HealthCenter with user name : "+ healthCenter.getName()+" already exist.");
	  	}

		return healthCenterRepository.save(healthCenter);
	}

	@Override
	public HealthCenter updateHealthCenter(UUID healthCenterId, HealthCenter updatedHealthCenter) {
		HealthCenter healthCenter = getHealthCenter(healthCenterId);
		modelMapper.map(updatedHealthCenter, healthCenter);
		return healthCenterRepository.save(healthCenter);
	}

	@Override
	public void deleteHealthCenter(UUID healthCenterId) {
		HealthCenter healthCenter = getHealthCenter(healthCenterId);
		healthCenterRepository.delete(healthCenter);
	}

}
