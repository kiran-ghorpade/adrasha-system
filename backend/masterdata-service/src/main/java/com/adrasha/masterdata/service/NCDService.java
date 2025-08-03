package com.adrasha.masterdata.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Example;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.masterdata.model.NCD;

public interface NCDService{
	
	List<NCD> getAll(Example<NCD> example);

	Long getCount(Example<NCD> example);
	
	NCD get(UUID id) throws NotFoundException;
	
	NCD create(NCD entity) throws AlreadyExistsException;
	
	NCD update(UUID id, NCD entity) throws NotFoundException;
	
	void delete(UUID id) throws NotFoundException;


}
