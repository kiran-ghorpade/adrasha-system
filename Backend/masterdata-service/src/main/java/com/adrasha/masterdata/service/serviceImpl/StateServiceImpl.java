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
import com.adrasha.masterdata.model.State;
import com.adrasha.masterdata.repository.StateRepository;
import com.adrasha.masterdata.service.StateService;

// TODO : Review class 
@Service
public class StateServiceImpl implements  StateService{

	@Autowired
	private StateRepository  stateRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Page<State> getAllStates(Pageable pageable, UUID countryId) {

		return stateRepository.findAll(pageable);
	}

	@Override
	public State getState(UUID stateId) {

		return stateRepository.findById(stateId)
				.orElseThrow(() -> new NotFoundException("State Not Found with id : " + stateId));
	}

	@Override
	public State createState(State state) {
		
		
		Optional<State> existingRequest = stateRepository.findByName(state.getName());
		
	  	if(existingRequest.isPresent()) {
	  		throw new AlreadyExistsException("State with name : "+ state.getName()+" already exist.");
	  	}

		return stateRepository.save(state);
	}

	@Override
	public State updateState(UUID stateId, State updatedState) {
		State state = getState(stateId);
		modelMapper.map(updatedState, state);
		return stateRepository.save(state);
	}

	@Override
	public void deleteState(UUID stateId) {
		State state = getState(stateId);
		stateRepository.delete(state);
	}

}
