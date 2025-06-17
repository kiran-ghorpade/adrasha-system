package com.adrasha.masterdata.base.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.adrasha.masterdata.base.model.MasterEntity;

@NoRepositoryBean
public interface MasterRepository<T extends MasterEntity> extends JpaRepository<T, UUID>{
	
	Optional<T> findByName(String name);
}
