package com.edigest.journelApp.respository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.edigest.journelApp.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

	User findByUsername(String username);
}
