package com.edigest.journelApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import com.edigest.journelApp.entity.JournalEntry;
import com.edigest.journelApp.entity.User;
import com.edigest.journelApp.respository.JournalEntryRepository;
import org.springframework.stereotype.Service;

@Service
public class JournalEntryService {

	@Autowired
	private JournalEntryRepository journalEntryRepository;
	
	@Autowired
	private UserService userService;


	public void saveEntry(JournalEntry journalEntry, String username) {
		User user = userService.findByUsername(username);
		journalEntry.setDate(LocalDateTime.now());
		JournalEntry saved = journalEntryRepository.save(journalEntry);
		user.getJournalEntries().add(saved);
		userService.saveUser(user);
	}
	
	public List<JournalEntry> getEntries(){
		return journalEntryRepository.findAll();
	}
	
	public Optional<JournalEntry> getById(ObjectId id) {
		return journalEntryRepository.findById(id);
	}
	
	public boolean deleteById(ObjectId id, String username) {
		boolean removed = false;
		User user = userService.findByUsername(username);
		removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
		if(removed) {
			userService.saveUser(user);
			journalEntryRepository.deleteById(id);
		}
		return removed;
	}
	
	public Optional<JournalEntry> updateById(JournalEntry myEntry) {
		 return Optional.ofNullable(journalEntryRepository.save(myEntry));
	}

	
}
