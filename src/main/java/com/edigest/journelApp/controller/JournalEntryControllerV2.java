package com.edigest.journelApp.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edigest.journelApp.entity.JournalEntry;
import com.edigest.journelApp.service.JournalEntryService;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

	@Autowired
	private JournalEntryService journalEntryService;
	
	@PostMapping("/save")
	public JournalEntry saveData(@RequestBody JournalEntry journalEntry) {
		journalEntry.setDate(LocalDateTime.now());
		journalEntryService.saveEntry(journalEntry);		
		return journalEntry;
	}
	
	@GetMapping("/get")
	public List<JournalEntry> getData(){
		List<JournalEntry> data = journalEntryService.getEntries();
		return data;
	}
	
	@GetMapping("/id/{myId}")
	public JournalEntry getById(@PathVariable ObjectId myId) {
		JournalEntry data = journalEntryService.getById(myId);
		return data;
	}
	
	@DeleteMapping("/id/{myId}")
	public String deleteById(@PathVariable ObjectId myId) {
		return journalEntryService.deleteById(myId);
	}
	
	@PutMapping("/id/{myId}")
	public JournalEntry updateById(@PathVariable ObjectId myId, @RequestBody JournalEntry myEntry) {
		JournalEntry data = journalEntryService.getById(myId);
		if(data != null) {
			data.setContent(myEntry.getContent() != null && !myEntry.getContent().equals("") ? myEntry.getContent() : data.getContent());
			data.setTitle(myEntry.getTitle() != null && !myEntry.getTitle().equals("") ? myEntry.getTitle() : data.getTitle());
		}
		
		myEntry.setDate(LocalDateTime.now());
		 
		return journalEntryService.updateById(data);
	}
}

//controller --> service --> repository
