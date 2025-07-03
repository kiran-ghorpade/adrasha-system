package com.adrasha.masterdata.service.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.masterdata.model.HealthCenter;
import com.adrasha.masterdata.repository.HealthCenterRepository;
import com.adrasha.masterdata.repository.LocationRepository;
import com.adrasha.masterdata.service.HealthCenterService;

import jakarta.transaction.Transactional;

@Service
public class HealthCenterServiceImpl implements HealthCenterService {

	@Autowired
	private HealthCenterRepository healthCenterRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public Page<HealthCenter> getAll(Example<HealthCenter> example, Pageable pageable) {

		return healthCenterRepository.findAll(example, pageable);
	}
	
	@Override
	public long getCount(Example<HealthCenter> example) {
		return healthCenterRepository.count(example);
	}


	@Override
	public HealthCenter get(UUID id) throws NotFoundException {
		return healthCenterRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("error.healthCenter.notFound"));
	}

	@Override
	@Transactional
	public HealthCenter create(HealthCenter entity) throws AlreadyExistsException {
		
		//checks if location exists
		if(!locationRepository.existsById(entity.getLocationId())) {
			throw new NotFoundException("error.location.notFound");
		}

		if(healthCenterRepository.existsByName(entity.getName())) {
			throw new AlreadyExistsException("error.healthCenter.notFound");
		}

		return healthCenterRepository.save(entity);
	}

	@Override
	@Transactional
	public HealthCenter update(UUID id, HealthCenter updatedEntity) throws NotFoundException {
		//checks if location exists
		if(!locationRepository.existsById(updatedEntity.getLocationId())) {
			throw new NotFoundException("error.location.notFound");
		}
		
		HealthCenter entity = get(id);
		mapper.map(updatedEntity, entity);
		return healthCenterRepository.save(entity);
	}

	@Override
	public void delete(UUID id) {
		HealthCenter entity = get(id);
		healthCenterRepository.delete(entity);
	}

}
