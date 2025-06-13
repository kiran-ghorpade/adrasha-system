package com.adrasha.masterdata.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.masterdata.model.State;

public interface StateService {
	// state
		Page<State> getAllStates(Pageable pageable, UUID countryId);
		
		State getState(UUID id) throws NotFoundException;
		
		State createState(State state) throws AlreadyExistsException;
		
		State updateState(UUID id, State state) throws NotFoundException;
		
		void deleteState(UUID id) throws NotFoundException;
}
