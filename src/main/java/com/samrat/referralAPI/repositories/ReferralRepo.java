package com.samrat.referralAPI.repositories;

import com.samrat.referralAPI.models.Referral;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReferralRepo extends MongoRepository<Referral, ObjectId> {

    Optional<Referral> findByUserId(ObjectId userId);
}
