package com.adrasha.masterdata.health.model;

import com.adrasha.masterdata.base.model.MasterEntity;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class NonCommunicableDisease extends MasterEntity{

}
