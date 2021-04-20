package com.old.school.selfdestructingmessage.core;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonFilter;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "notes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@Validated
@JsonFilter("NoteFilter")
public class Note{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@Size(max = 50)
	private String name;
	
	@Size(max = 500)
	@NotBlank
	private String message;
	
	@NotBlank
	@Size(max = 64)
	private String password;

	@Email
	@Size(max=50)
	private String email;
	
	private Timestamp generationTime;
	
	private Timestamp destructionTime;
	
	private boolean isActive;
	
}
