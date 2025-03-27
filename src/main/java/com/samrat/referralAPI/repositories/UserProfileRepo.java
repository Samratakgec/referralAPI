package com.samrat.referralAPI.repositories;

import com.samrat.referralAPI.models.UserProfile;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoAdminOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepo extends MongoRepository<UserProfile, ObjectId> {
}
