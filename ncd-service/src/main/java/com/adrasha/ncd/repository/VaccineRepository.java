package com.adrasha.ncd.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrasha.ncd.model.Vaccine;

public interface VaccineRepository extends JpaRepository<Vaccine, UUID>{

}
