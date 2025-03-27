package com.samrat.referralAPI.utils;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class UserSession {
    private ObjectId userObjectId = null; // Initially null

    public ObjectId getUserObjectId() {
        return userObjectId;
    }

    public void setUserObjectId(ObjectId userObjectId) {
        this.userObjectId = userObjectId;
    }

    public void clearSession() {
        this.userObjectId = null;
    }
}
