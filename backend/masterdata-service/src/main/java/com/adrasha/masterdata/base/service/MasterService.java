package com.adrasha.masterdata.base.service;

import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.masterdata.base.model.MasterEntity;

public interface MasterService<T extends MasterEntity> {

		Page<T> getAll(Pageable pageable);
		
		Page<T> getAll(Example<T> example, Pageable pageable);
		
		T get(UUID id) throws NotFoundException;
		
		T create(T entity) throws AlreadyExistsException;
		
		T update(UUID id, T entity) throws NotFoundException;
		
		void delete(UUID id) throws NotFoundException;
}
