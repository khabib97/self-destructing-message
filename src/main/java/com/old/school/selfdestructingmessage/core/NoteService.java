package com.old.school.selfdestructingmessage.core;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.old.school.selfdestructingmessage.common.Utils;

@Service
public class NoteService {
	
	@Autowired
	private NoteRepository noteRepository;
	
	public List<Note> findAll(){
		return noteRepository.findAll();
	}

	public Note save(@Valid Note note){
		
		note.setGenerationTime(Utils.getCurrentTime());
		note.setPassword(Utils.textToSHA(note.getPassword()));
		
		return noteRepository.save(note);
	}
	
	
}
