package com.edigest.journelApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edigest.journelApp.entity.JournalEntry;
import com.edigest.journelApp.entity.User;
import com.edigest.journelApp.respository.JournalEntryRepository;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JournalEntryService {

	@Autowired
	private JournalEntryRepository journalEntryRepository;
	
	@Autowired
	private UserService userService;

	@Transactional
	public void saveEntry(JournalEntry journalEntry, String username) {
		User user = userService.findByUsername(username);
		journalEntry.setDate(LocalDateTime.now());
		JournalEntry saved = journalEntryRepository.save(journalEntry);
		user.getJournalEntries().add(saved);
		userService.saveEntry(user);
	}
	
	public List<JournalEntry> getEntries(){
		return journalEntryRepository.findAll();
	}
	
	public Optional<JournalEntry> getById(ObjectId id) {
		return journalEntryRepository.findById(id);
	}
	
	public void deleteById(ObjectId id, String username) {
		User user = userService.findByUsername(username);
		user.getJournalEntries().removeIf(x -> x.getId().equals(id));
		userService.saveEntry(user);
		journalEntryRepository.deleteById(id);	
	}
	
	public Optional<JournalEntry> updateById(JournalEntry myEntry) {
		 return Optional.ofNullable(journalEntryRepository.save(myEntry));
	}

	
}
