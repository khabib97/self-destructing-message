package com.old.school.selfdestructingmessage.core;

import java.util.List;
import java.util.Optional;

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

	public Note save(@Valid Note note) throws Exception{
		String password = note.getPassword();
		String message = note.getMessage();
		
		note.setMessage(Utils.encrypt(message, password));
		note.setGenerationTime(Utils.getCurrentTime());
		note.setPassword(Utils.textToSHA(password));
		note.setActive(true);
		
		return noteRepository.save(note);
	}



	public Note getNoteByIdAndPassword(String id, String password) throws Exception {
		if(password == null || password.isEmpty())
			throw new Exception("Please Provide Password");
		
		Optional<Note> optionalNote = noteRepository.findByIdIfActive(id, true);
		if (!optionalNote.isPresent()) {
			throw new Exception("Message Not Exist");
		}
		
		Note note = optionalNote.get();
		
		if(!note.getPassword().equals(Utils.textToSHA(password))) {
			throw new Exception("Invalid Password");
		}
		
		String encryptMsg = note.getMessage();
		String originalMsg = Utils.decrypt(note.getMessage(), password);
		
		updateNote(note, encryptMsg);

		note.setMessage(originalMsg);
		return note;
		
	}
	
	public void updateNote(Note note,String encryptMsg ) {
		
		note.setActive(false);
		note.setMessage(encryptMsg);
		note.setDestructionTime(Utils.getCurrentTime());
		
		noteRepository.save(note);
		
	}
	
	
}
