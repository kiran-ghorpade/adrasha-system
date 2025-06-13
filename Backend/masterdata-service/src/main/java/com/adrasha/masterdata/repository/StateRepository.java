package com.adrasha.masterdata.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adrasha.masterdata.model.State;



@Repository
public interface StateRepository extends JpaRepository<State, UUID>{

	Optional<State> findByName(String name);
}
