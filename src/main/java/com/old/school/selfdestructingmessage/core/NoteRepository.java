package com.old.school.selfdestructingmessage.core;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoteRepository extends JpaRepository<Note, String> {

	List<Note> findAll();
	
	@Query("SELECT u FROM Note u WHERE u.id = :id and u.isActive = :isActive")
	Optional<Note> findByIdIfActive(@Param("id")String id, @Param("isActive") boolean isActive);
	
}
