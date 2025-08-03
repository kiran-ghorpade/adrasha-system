package com.adrasha.data.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

import com.adrasha.core.model.AliveStatus;
import com.adrasha.core.model.Auditable;
import com.adrasha.core.model.Gender;
import com.adrasha.core.model.Name;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Member extends Auditable{

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private UUID familyId;
	
	@Column(nullable = false)
	private UUID ashaId;
	
	@Embedded
	private Name name;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	private LocalDate dateOfBirth;

	private String birthPlace;
	
	@Transient
	private Integer age;

	@Column(unique = true)
	private String adharNumber;

	private String abhaNumber;

	private String mobileNumber;

	@Enumerated
	private AliveStatus aliveStatus;
	
	
	public Integer getAge() {
		return Period.between(dateOfBirth, LocalDate.now()).getYears();
	}

}
