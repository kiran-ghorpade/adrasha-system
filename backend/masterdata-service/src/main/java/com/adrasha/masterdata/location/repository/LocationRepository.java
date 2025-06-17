package com.adrasha.masterdata.location.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.adrasha.masterdata.base.repository.MasterRepository;
import com.adrasha.masterdata.location.model.Location;
import com.adrasha.masterdata.location.model.Subdistrict;


@Repository
public interface LocationRepository extends MasterRepository<Location>{
	
	Page<Location> findAllBySubdistrict(Pageable pageable, Subdistrict subdistrict);
}
