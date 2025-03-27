package com.samrat.referralAPI.controllers;

import com.samrat.referralAPI.models.UserProfile;
import com.samrat.referralAPI.services.referral.ReferralInterface;
import com.samrat.referralAPI.services.userProfile.UserProfileInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping ("/api/userProfile")
public class UserProfileController {
    @Autowired
    UserProfileInterface userProfileService ;
    @Autowired
    ReferralInterface referralService ;

    @PostMapping("/new")
    @Transactional
    public ResponseEntity<?> createUserProfileAndLinkup (@RequestBody UserProfile userProfile)
    {
        String resp1 = userProfileService.createUserProfileAndLinkup(userProfile);
        if (Objects.equals(resp1, "200"))
        {
            String resp2 = referralService.updateEntryForSuccessfulAndRemoveFromPending() ;
            if (Objects.equals(resp2, "200"))
            {

                return ResponseEntity.status(200).body("User Profile created ");
            }
            else
            {
                return ResponseEntity.status(Integer.parseInt(resp2)).body("rollback") ;
            }
        }

        return ResponseEntity.status(Integer.parseInt(resp1)).body("rollback") ;
    }
}
