package com.adrasha.analytics.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrasha.analytics.model.AliveStatusCount;

public interface AliveStatusCountRepository extends JpaRepository<AliveStatusCount, UUID> {

}
