package com.adrasha.masterdata.health.repository;

import org.springframework.stereotype.Repository;

import com.adrasha.masterdata.base.repository.MasterRepository;
import com.adrasha.masterdata.health.model.HealthCenter;


@Repository
public interface HealthCenterRepository extends MasterRepository<HealthCenter>{
	
}
