package com.edigest.journelApp.controller;

import com.edigest.journelApp.entity.JournalEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
	
	private Map<Long, JournalEntry> journalEntry = new HashMap<>();
	
	@GetMapping()
	public List<JournalEntry> getAll() {
		return new ArrayList<>(journalEntry.values());
	}
	
	@PostMapping()
	public boolean createEntry(@RequestBody JournalEntry journal) {
		journalEntry.put(journal.getId(), journal);
		return true;
	}
	
	@GetMapping("id/{myId}")
	public JournalEntry getById(@PathVariable Long myId) {
		return journalEntry.get(myId);
	}
	
	@DeleteMapping("id/{myId}")
	public JournalEntry deleteById(@PathVariable Long myId) {
		return journalEntry.remove(myId);
	}

}
