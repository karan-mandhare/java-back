package com.edigest.journelApp.controller;

import com.edigest.journelApp.entity.JournalEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
	
	private Map<Long, JournalEntry> journalEntry = new HashMap<>();
	
	@GetMapping
	public List<JournalEntry> getAll(){
		return new ArrayList<>(journalEntry.values());
	}
	
	@PostMapping
	public void createEntry() {
		
	}

}
