package com.old.school.selfdestructingmessage.core;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
public class NoteController {
	
	@Autowired
	private NoteService noteService;
	
	@GetMapping("/notes")
	public List<Note> getNotes() {
		return noteService.findAll();	
	}
	
	@PostMapping("/notes")
	public ResponseEntity<Object> createUser(@Valid @RequestBody Note note) {
		Note savedNote = noteService.save(note);

		// return status of created user
		// build uri
		// created status code : 201 with Location
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedNote.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}
}
