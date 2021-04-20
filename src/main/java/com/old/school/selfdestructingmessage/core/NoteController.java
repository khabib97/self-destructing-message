package com.old.school.selfdestructingmessage.core;

import java.net.URI;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.old.school.selfdestructingmessage.common.FilterUtil;


@RestController
public class NoteController {
	
	@Autowired
	private NoteService noteService;
	@Autowired
	private FilterUtil filterUtil;
	
	@GetMapping("/notes")
	public MappingJacksonValue getNotes() {
		
		List<Note> notes = noteService.findAll();	    
	    
		return filterUtil.dynamicSerializeAllExcept(notes, Note.class, Set.of("password","active"));	
	}
	
	@GetMapping("/notes/{id}")
	public MappingJacksonValue getNoteUsingPassword(@PathVariable String id, @RequestBody Note note) throws Exception {
		Note decryptedNote =  noteService.getNoteByIdAndPassword(id,note.getPassword());	
		return filterUtil.dynamicFilterOutAllExcept(decryptedNote, Note.class, Set.of("name","message"));
	}
	
	@PostMapping("/notes")
	public ResponseEntity<Object> createUser(@Valid @RequestBody Note note) throws Exception {
		Note savedNote = noteService.save(note);

		// return status of created user
		// build uri
		// created status code : 201 with Location
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedNote.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}
}
