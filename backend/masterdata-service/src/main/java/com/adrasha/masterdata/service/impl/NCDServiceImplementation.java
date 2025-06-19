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
import com.adrasha.masterdata.model.NCD;
import com.adrasha.masterdata.repository.NCDRepository;
import com.adrasha.masterdata.service.NCDService;

import jakarta.transaction.Transactional;

@Service
public class NCDServiceImplementation  implements NCDService{


	@Autowired
	private NCDRepository ncdRepository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public Page<NCD> getAll(Example<NCD> example, Pageable pageable) {
		
		return ncdRepository.findAll(example, pageable);
	}

	@Override
	public NCD get(UUID id) throws NotFoundException {
		return ncdRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("NonCommunicableDisease Not Found with id : " + id));
	}

	@Override
	@Transactional
	public NCD create(NCD entity) throws AlreadyExistsException {
		Optional<NCD> exsistingFamily = ncdRepository.findByName(entity.getName());
		
		if(exsistingFamily.isPresent()) {
			throw new AlreadyExistsException("NonCommunicableDisease with name : "+ entity.getName()+" already present");
		}
		
		return ncdRepository.save(entity);
	}

	@Override
	@Transactional
	public NCD update(UUID id, NCD updatedEntity) throws NotFoundException {
		NCD entity = get(id);
		mapper.map(updatedEntity, entity);
		return ncdRepository.save(entity);
	}
	
	@Override
	public void delete(UUID id) {
		NCD entity = get(id);
		ncdRepository.delete(entity);
	}
}
