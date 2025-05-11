package com.adrasha.pregnancyservice.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@NotNull
	@Embedded
	private Name name;

	@NotNull
	@Enumerated(EnumType.STRING)
	private String gender;

	@Past
	private Instant dateOfBirth;

	private String birthPlace;

	@Size(min = 10, max = 12)
	@Column(unique = true)
	private String adharNumber;

	@Column(unique = true)
	private String abhaNumber;

	@Pattern(regexp = "^[6-9]\\d{9}$")
	private String mobileNumber;

	private boolean maritalStatus;

	@PastOrPresent
	private Instant marriageDate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "spouse_id")
	private Member spouse;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "father_id")
	private Member father;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mother_id")
	private Member mother;

	@NotNull
	@Default
	private boolean alive = true;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(nullable = false)
	private Family family;
}
