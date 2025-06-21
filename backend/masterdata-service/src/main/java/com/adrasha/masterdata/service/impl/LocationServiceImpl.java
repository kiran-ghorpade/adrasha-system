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
import com.adrasha.masterdata.model.Location;
import com.adrasha.masterdata.repository.LocationRepository;
import com.adrasha.masterdata.service.LocationService;

import jakarta.transaction.Transactional;

@Service
public class LocationServiceImpl implements LocationService{


	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public Page<Location> getAll(Example<Location> example, Pageable pageable) {
		
		return locationRepository.findAll(example, pageable);
	}
	
	@Override
	public long getCount(Example<Location> example) {
		return locationRepository.count(example);
	}

	@Override
	public Location get(UUID id) throws NotFoundException {
		return locationRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Location Not Found with id : " + id));
	}

	@Override
	@Transactional
	public Location create(Location entity) throws AlreadyExistsException {
		
		if(locationRepository.existsByPincode(entity.getPincode())) {
			throw new AlreadyExistsException("Location with pincode : "+ entity.getPincode()+" already present");
		}
		
		return locationRepository.save(entity);
	}

	@Override
	@Transactional
	public Location update(UUID id, Location updatedEntity) throws NotFoundException {
		Location entity = get(id);
		mapper.map(updatedEntity, entity);
		return locationRepository.save(entity);
	}
	
	@Override
	public void delete(UUID id) {
		Location entity = get(id);
		locationRepository.delete(entity);
	}

}
