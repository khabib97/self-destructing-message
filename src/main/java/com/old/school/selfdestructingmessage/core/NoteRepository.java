package com.old.school.selfdestructingmessage.core;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, String> {

	List<Note> findAll();

}
