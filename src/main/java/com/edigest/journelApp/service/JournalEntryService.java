package com.edigest.journelApp.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edigest.journelApp.entity.JournalEntry;
import com.edigest.journelApp.respository.JournalEntryRepository;

@Component
public class JournalEntryService {

	@Autowired
	private JournalEntryRepository journalEntryRepository;
	
	public void saveEntry(JournalEntry journalEntry) {		
		journalEntryRepository.save(journalEntry);
	}
	
	public List<JournalEntry> getEntries(){
		return journalEntryRepository.findAll();
	}
	
	public JournalEntry getById(ObjectId id) {
		return journalEntryRepository.findById(id).orElse(null);
	}
	
	public String deleteById(ObjectId id) {
		journalEntryRepository.deleteById(id);
		return "deleted successfully";
	}
	
	public JournalEntry updateById(JournalEntry myEntry) {
		return journalEntryRepository.save(myEntry);
	}
}
