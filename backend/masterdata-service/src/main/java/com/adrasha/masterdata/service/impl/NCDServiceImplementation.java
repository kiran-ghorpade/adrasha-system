package com.adrasha.masterdata.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
	public List<NCD> getAll(Example<NCD> example) {
		
		return ncdRepository.findAll(example);
	}

	@Override
	public Long getCount(Example<NCD> example) {
		return ncdRepository.count(example);
	}

	@Override
	public NCD get(UUID id) throws NotFoundException {
		return ncdRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("error.NCD.notFound"));
	}

	@Override
	@Transactional
	public NCD create(NCD entity) throws AlreadyExistsException {
		
		if(ncdRepository.existsByName(entity.getName())) {
			throw new AlreadyExistsException("error.NCD.alreadyExists");
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
