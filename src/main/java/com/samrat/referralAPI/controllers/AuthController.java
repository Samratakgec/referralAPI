package com.samrat.referralAPI.controllers;

import com.samrat.referralAPI.models.LogInPayload;
import com.samrat.referralAPI.models.Referral;
import com.samrat.referralAPI.models.SignUpPayload;
import com.samrat.referralAPI.repositories.UserRepo;
import com.samrat.referralAPI.services.referral.ReferralService;
import com.samrat.referralAPI.services.user.UserService;
import com.samrat.referralAPI.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    JwtUtil jwtUtil ;

    @Autowired
    UserService userService ;
    @Autowired
    ReferralService referralService ;

    @PostMapping ("/signup")
    public ResponseEntity<?> signUp (@RequestBody SignUpPayload signUpPayload)
    {
        // validate refGain
        if (signUpPayload.getRefGain() != null)
        {
            String resp1 =referralService.validateRefGain(signUpPayload.getRefGain()) ;
            if (Objects.equals(resp1, "404"))
            {
                return ResponseEntity.status(404).body("invalid") ;
            }
            else if (Objects.equals(resp1, "500"))
            {
                return ResponseEntity.status(500).body("some error-occurred") ;
            }
        }
        String resp = userService.createUser(signUpPayload) ;
        if (Objects.equals(resp, "409") || Objects.equals(resp, "404") || Objects.equals(resp, "500")) {
            return  ResponseEntity.status(500).body("error") ;
        }
        return ResponseEntity.ok(Map.of(
                "message", "Signup successful",
                "token", resp
        ));

    }

    @GetMapping ("/login")
    public ResponseEntity<?> login (@RequestBody LogInPayload logInPayload)
    {
        String resp = userService.loginUser(logInPayload) ;
        if (Objects.equals(resp, "409") || Objects.equals(resp, "404") || Objects.equals(resp, "500")) {
            return  ResponseEntity.status(500).body("error") ;
        }
        return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "token", resp
        ));

    }


}
