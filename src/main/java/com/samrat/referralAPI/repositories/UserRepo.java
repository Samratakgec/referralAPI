package com.samrat.referralAPI.repositories;

import com.samrat.referralAPI.models.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<User, ObjectId> {

    Optional<User> findByEmail(String email);
}

