package com.samrat.referralAPI.services.userProfile;

import com.samrat.referralAPI.models.UserProfile;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
public interface UserProfileInterface {
    String createUserProfileAndLinkup (UserProfile userProfile) ;

}
