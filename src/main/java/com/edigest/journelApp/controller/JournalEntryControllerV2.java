package com.edigest.journelApp.controller;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	
	@PostMapping("/save")
	public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry) {
		try {
			Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
			String userName = authUser.getName();
			journalEntryService.saveEntry(journalEntry, userName);
			return new ResponseEntity<JournalEntry>(journalEntry, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/get")
	public ResponseEntity<?> getAllJournalEntriesOfUser(){
		try {
			Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
			String userName = authUser.getName();
			User user = userService.findByUsername(userName);
			List<JournalEntry>  data = user.getJournalEntries();
			return new ResponseEntity<>(data, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/id/{myId}")
	public ResponseEntity<JournalEntry> getById(@PathVariable ObjectId myId) {
		try {
			Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
			String userName = authUser.getName();
			User user = userService.findByUsername(userName);
			List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
			if(!collect.isEmpty()){
				Optional<JournalEntry> data = journalEntryService.getById(myId);
				if(data.isPresent()) {
					return new ResponseEntity<JournalEntry>(data.get(), HttpStatus.OK);
				}
			}

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@DeleteMapping("/id/{myId}")
	public ResponseEntity<?> deleteById(@PathVariable ObjectId myId) {
		Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
		String userName = authUser.getName();
		boolean remove = journalEntryService.deleteById(myId, userName);
		if(remove) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/id/{myId}")
	public ResponseEntity<?> updateById( @PathVariable ObjectId myId, @RequestBody JournalEntry myEntry) {
		Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
		String userName = authUser.getName();
		JournalEntry data = journalEntryService.getById(myId).orElse(null);
		
		if(data != null) {
			data.setContent(myEntry.getContent() != null && !myEntry.getContent().equals("") ? myEntry.getContent() : data.getContent());
			data.setTitle(myEntry.getTitle() != null && !myEntry.getTitle().equals("") ? myEntry.getTitle() : data.getTitle());
			
			journalEntryService.saveEntry(data, userName);
			
			return new ResponseEntity<>(HttpStatus.OK);
		} 
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}

//controller --> service --> repository
