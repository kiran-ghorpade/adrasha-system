package com.adrasha.masterdata.service;

import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.masterdata.model.Location;

public interface LocationService {
	
	Page<Location> getAll(Example<Location> example, Pageable pageable);

	Long getCount(Example<Location> example);
	
	Location get(UUID id) throws NotFoundException;
	
	Location create(Location entity) throws AlreadyExistsException;
	
	Location update(UUID id, Location entity) throws NotFoundException;
	
	void delete(UUID id) throws NotFoundException;


}
