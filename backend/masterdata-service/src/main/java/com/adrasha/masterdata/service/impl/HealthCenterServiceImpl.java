package com.adrasha.masterdata.service.impl;

import java.util.Optional;
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
import com.adrasha.masterdata.service.HealthCenterService;
import com.adrasha.masterdata.service.LocationService;

import jakarta.transaction.Transactional;

@Service
public class HealthCenterServiceImpl implements HealthCenterService {

	@Autowired
	private HealthCenterRepository healthCenterRepository;

	@Autowired
	private LocationService locationService;

	@Autowired
	private ModelMapper mapper;

	@Override
	public Page<HealthCenter> getAll(Example<HealthCenter> example, Pageable pageable) {

		return healthCenterRepository.findAll(example, pageable);
	}

	@Override
	public HealthCenter get(UUID id) throws NotFoundException {
		return healthCenterRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("HealthCenter Not Found with id : " + id));
	}

	@Override
	@Transactional
	public HealthCenter create(HealthCenter entity) throws AlreadyExistsException {
		
		//checks if location exists
		locationService.get(entity.getLocationId());

		Optional<HealthCenter> exsistingFamily = healthCenterRepository.findByName(entity.getName());

		if (exsistingFamily.isPresent()) {
			throw new AlreadyExistsException("HealthCenter with name : " + entity.getName() + " already present");
		}

		return healthCenterRepository.save(entity);
	}

	@Override
	@Transactional
	public HealthCenter update(UUID id, HealthCenter updatedEntity) throws NotFoundException {
		//checks if location exists
		locationService.get(updatedEntity.getLocationId());
		
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
