package com.samrat.referralAPI.services.userProfile;

import com.samrat.referralAPI.models.User;
import com.samrat.referralAPI.models.UserProfile;
import com.samrat.referralAPI.repositories.UserProfileRepo;
import com.samrat.referralAPI.utils.UserSession;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserProfileService implements UserProfileInterface{
    @Autowired
    UserProfileRepo userProfileRepo ;
    @Autowired
    com.samrat.referralAPI.repositories.UserRepo userRepo ;
    @Autowired
    UserSession userSession ;
    @Override
    @Transactional
    public String createUserProfileAndLinkup(UserProfile userProfile) {
        try {
            // fetch userId
            ObjectId userId = userSession.getUserObjectId() ;
            if (userId == null) return  null ;


            // link profile's userId with user's object id
            userProfile.setUserId(userId);

            // create UserProfile in db
            UserProfile savedUserProfile = userProfileRepo.save(userProfile) ;


            // link user's profile id with profile's object id
            Optional<User> user = userRepo.findById(userId);
            if (user.isPresent())
            {
                User fetchedUser = user.get();
                fetchedUser.setProfileId(savedUserProfile.getId());

                userRepo.save(fetchedUser) ;
                return  "200" ;
            }
            else  return  "404" ;

        }
        catch (Exception e) {
            return "500";
        }
    }
}
