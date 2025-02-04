package com.edigest.journelApp.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.edigest.journelApp.entity.JournalEntry;
import com.edigest.journelApp.entity.User;
import com.edigest.journelApp.service.JournalEntryService;
import com.edigest.journelApp.service.UserService;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

	@Autowired
	private JournalEntryService journalEntryService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/save/{username}")
	public ResponseEntity<JournalEntry> saveData(@RequestBody JournalEntry journalEntry, @PathVariable
			String username) {
		try {				
			journalEntryService.saveEntry(journalEntry, username);
			return new ResponseEntity<JournalEntry>(journalEntry, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/get/{username}")
	public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username){
		try {
			User user = userService.findByUsername(username);
		
			List<JournalEntry>  data = user.getJournalEntries();
			
			return new ResponseEntity<>(data, HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/id/{myId}")
	public ResponseEntity<JournalEntry> getById(@PathVariable ObjectId myId) {
		try {			
			Optional<JournalEntry> data = journalEntryService.getById(myId);
			if(data.isPresent()) {
				return new ResponseEntity<JournalEntry>(data.get(), HttpStatus.OK);
			} 
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@DeleteMapping("/id/{username}/{myId}")
	public ResponseEntity<?> deleteById(@PathVariable ObjectId myId, @PathVariable String username) {
		journalEntryService.deleteById(myId, username);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/id/{username}/{myId}")
	public ResponseEntity<?> updateById(@PathVariable String username, @PathVariable ObjectId myId, @RequestBody JournalEntry myEntry) {
		JournalEntry data = journalEntryService.getById(myId).orElse(null);
		
		if(data != null) {
			data.setContent(myEntry.getContent() != null && !myEntry.getContent().equals("") ? myEntry.getContent() : data.getContent());
			data.setTitle(myEntry.getTitle() != null && !myEntry.getTitle().equals("") ? myEntry.getTitle() : data.getTitle());
			
//			journalEntryService.saveEntry(data);
			
			return new ResponseEntity<>(HttpStatus.OK);
		} 
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}

//controller --> service --> repository
