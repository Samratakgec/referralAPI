package com.samrat.referralAPI.controllers;

import com.samrat.referralAPI.models.UserProfile;
import com.samrat.referralAPI.services.referral.ReferralInterface;
import com.samrat.referralAPI.services.user.UserService;
import com.samrat.referralAPI.services.userProfile.UserProfileInterface;
import com.samrat.referralAPI.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping ("/api/user")
public class UserProfileController {
    @Autowired
    JwtUtil jwtUtil ;
    @Autowired
    UserProfileInterface userProfileService ;
    @Autowired
    ReferralInterface referralService ;
    @Autowired
    UserService userService ;

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

    @PostMapping ("/gen-ref-code")
    public ResponseEntity<?> genRefCode (@RequestHeader("Authorization") String token){
        System.out.println(token);
        if (jwtUtil.isTokenValid(token))
        {
            String email = jwtUtil.extractEmail(token);
            System.out.println("this works1");
            referralService.createReferral(email) ;
            System.out.println("this works2");
            String resp = userService.updateUserRefGen(email) ;
            if (Objects.equals(resp,"404-1") || Objects.equals(resp,"404-2") || Objects.equals(resp,"500")){
                return ResponseEntity.status(500).body("error-occurred") ;
            }
            return ResponseEntity.ok(Map.of(
                    "message", "ref-code generated successfully",
                    "ref-code", resp
            ));
        }
        System.out.println("jwt moye moye");
        return ResponseEntity.badRequest().body("JWT invalid") ;
    }
}
