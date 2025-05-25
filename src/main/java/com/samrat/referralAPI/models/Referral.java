package com.samrat.referralAPI.models;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "referrals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Referral {
    @Id
    private ObjectId refcode;  // MongoDB ObjectId - referral code

    private ObjectId userId;  // Reference to the user who generated the referral


    private int usageCount;  // Number of times the referral has been used successfully

    private List<ObjectId> successfulUsers;  // List of users who successfully used the referral
    private List<ObjectId> pendingUsers;  // List of users who signed up but haven't completed their profile

    public ObjectId getRefcode() {
        return refcode;
    }

    public void setRefcode(ObjectId refcode) {
        this.refcode = refcode;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
    }

    public List<ObjectId> getSuccessfulUsers() {
        return successfulUsers;
    }

    public void setSuccessfulUsers(List<ObjectId> successfulUsers) {
        this.successfulUsers = successfulUsers;
    }

    public List<ObjectId> getPendingUsers() {
        return pendingUsers;
    }

    public void setPendingUsers(List<ObjectId> pendingUsers) {
        this.pendingUsers = pendingUsers;
    }
}
