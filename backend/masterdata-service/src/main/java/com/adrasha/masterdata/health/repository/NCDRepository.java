package com.adrasha.masterdata.health.repository;

import org.springframework.stereotype.Repository;

import com.adrasha.masterdata.base.repository.MasterRepository;
import com.adrasha.masterdata.health.model.NonCommunicableDisease;

@Repository
public interface NCDRepository extends MasterRepository<NonCommunicableDisease> {

}
