package com.adrasha.healthprofile.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrasha.healthprofile.model.Asha;

public interface AshaRepository extends JpaRepository<Asha, UUID>{

}
