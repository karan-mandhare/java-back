package com.edigest.journelApp.respository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.edigest.journelApp.entity.JournalEntry;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {

	Optional<JournalEntry> save(Optional<JournalEntry> myEntry);

}
