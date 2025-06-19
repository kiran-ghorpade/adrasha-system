package com.adrasha.masterdata.service;

import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.masterdata.model.NCD;

public interface NCDService{
	
	Page<NCD> getAll(Example<NCD> example, Pageable pageable);
	
	NCD get(UUID id) throws NotFoundException;
	
	NCD create(NCD entity) throws AlreadyExistsException;
	
	NCD update(UUID id, NCD entity) throws NotFoundException;
	
	void delete(UUID id) throws NotFoundException;

}
