package com.adrasha.monolithic.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrasha.monolithic.model.Asha;

public interface AshaRepository extends JpaRepository<Asha, UUID>{

}
