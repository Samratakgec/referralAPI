package com.samrat.referralAPI.controllers;

import com.samrat.referralAPI.models.User;
import com.samrat.referralAPI.services.referral.ReferralInterface;
import com.samrat.referralAPI.services.user.UserInterface;
import com.samrat.referralAPI.utils.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping ("/api/user")
public class UserController {
    @Autowired
    UserInterface userService ;
    @Autowired
    ReferralInterface referralService ;
    @Autowired
    UserSession userSession ;

    @PostMapping ("/sign-up")
    @Transactional
    public ResponseEntity<?> createUser (@RequestBody User user)
    {
        // validate refGain

        if (user.getRefGain() != null)
        {
            String resp1 =referralService.validateRefGain(user.getRefGain()) ;
            if (Objects.equals(resp1, "404"))
            {
                return ResponseEntity.status(404).body("No referral code found! Try again");
            }
            else if (Objects.equals(resp1, "500"))
            {
                return ResponseEntity.status(500).body("Unexpected error- rollback") ;
            }
        }


        // db entry of user
        String resp2 = userService.createUser(user) ;

        if (Objects.equals(resp2, "200"))
        {
            // update ref in pending users
            if (user.getRefGain()!= null)
            {
                String  resp3 = referralService.updateEntryForPending(user.getRefGain()) ;
                if (Objects.equals(resp3, "404"))
                {
                    return ResponseEntity.status(404).body("No referral found -rollback") ;
                }
                else if (Objects.equals(resp3, "500"))
                {
                    return ResponseEntity.status(500).body("unexpected error -rollback") ;
                }
            }
            // db entry of referral
            String resp3 = referralService.createReferral() ;
            if (Objects.equals(resp3, null))
            {
                return ResponseEntity.status(500).body("user should sign-up") ;
            }
            else if (Objects.equals(resp3, "200"))
            return ResponseEntity.ok("user and it's referral created with user's object id : "+ userSession.getUserObjectId()) ;
            else if (resp3.equals("409"))
                return ResponseEntity.status(409).body("same referral found -rollback");
            else
                return  ResponseEntity.status(500).body("unexpected error -rollback") ;
        }
        else if (Objects.equals(resp2, "409"))
        {
            return ResponseEntity.status(409).body("user with this email already exists") ;
        }
        else
        return ResponseEntity.status(500).body("Internal Server Error");
    }

    @GetMapping ("/login")
    public ResponseEntity<?> loginUser (@RequestBody User user)
    {
        String resp = userService.loginUser(user);
        if (Objects.equals(resp, "200"))
        {
           return ResponseEntity.status(200).body("Login successful") ;
        }
        return ResponseEntity.status(Integer.parseInt(resp)).body("check status code") ;
    }
    @GetMapping ("logout")
    public ResponseEntity<?> logOutUser ()
    {
        userSession.clearSession();
        return ResponseEntity.ok().body("log-out successfully") ;
    }
}
