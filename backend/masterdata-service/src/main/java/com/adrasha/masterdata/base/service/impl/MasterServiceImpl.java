package com.adrasha.masterdata.base.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.masterdata.base.model.MasterEntity;
import com.adrasha.masterdata.base.repository.MasterRepository;
import com.adrasha.masterdata.base.service.MasterService;

public abstract class MasterServiceImpl<T extends MasterEntity> implements MasterService<T>{
	
	@Autowired
	private MasterRepository<T> masterRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Page<T> getAll(Pageable pageable) {
		return masterRepository.findAll(pageable);
	}
	
	@Override
	public Page<T> getAll(Example<T> example, Pageable pageable) {
		return masterRepository.findAll(example, pageable);
	}

	@Override
	public T get(UUID id) {

		return masterRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Not Found with id : " + id));
	}

	@Override
	public T create(T entity) {
		
		Optional<T> existingRequest = masterRepository.findByName(entity.getName());
		
	  	if(existingRequest.isPresent()) {
	  		throw new AlreadyExistsException(entity.getName()+" already exist.");
	  	}

		return masterRepository.save(entity);
	}

	@Override
	public T update(UUID id, T updatedEntity) {
		T entity = get(id);
		modelMapper.map(updatedEntity, entity);
		return masterRepository.save(entity);
	}

	@Override
	public void delete(UUID id) {
		T entity = get(id);
		masterRepository.delete(entity);
	}
}
