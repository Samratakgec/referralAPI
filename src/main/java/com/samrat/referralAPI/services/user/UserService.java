package com.samrat.referralAPI.services.user;

import com.samrat.referralAPI.models.LogInPayload;
import com.samrat.referralAPI.models.Referral;
import com.samrat.referralAPI.models.SignUpPayload;
import com.samrat.referralAPI.models.User;
import com.samrat.referralAPI.repositories.ReferralRepo;
import com.samrat.referralAPI.repositories.UserRepo;
import com.samrat.referralAPI.utils.JwtUtil;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService implements UserInterface {
    @Autowired
    JwtUtil jwtUtil ;

    @Autowired
    UserRepo userRepo ;

    @Autowired
    PasswordEncoder passwordEncoder ;

    @Autowired
    ReferralRepo referralRepo ;


    @Override
    public String createUser(SignUpPayload signUpPayload) {
        try {
            // check for existing user
            if (userRepo.findByEmail(signUpPayload.getEmail()).isPresent()){
                return "409" ;
            }

            // db entry of user
            User newUser = new User();
            newUser.setEmail(signUpPayload.getEmail());
            newUser.setPassword(passwordEncoder.encode(signUpPayload.getPassword()));
            newUser.setRole(signUpPayload.getRole()) ;
            newUser.setRefGen(null);
            newUser.setRefGain(signUpPayload.getRefGain());

            userRepo.save(newUser) ;

            // Generate jwt and return token
            return jwtUtil.generateToken(newUser.getEmail(), newUser.getRole());

        } catch (Exception e) {
            return "500"; // Return 500 in case of an exception
        }
    }

    @Override
    public String loginUser (LogInPayload logInPayload)
    {
        try{
            Optional<User> optionalUser = userRepo.findByEmail(logInPayload.getEmail()) ;
            if (optionalUser.isEmpty()) {
                return "404";
            }
            User user = optionalUser.get() ;
            if (!passwordEncoder.matches(logInPayload.getPassword(), user.getPassword())) {
                return "409" ;
            }
            return jwtUtil.generateToken(user.getEmail(), user.getRole());
        }
        catch (Exception e)
        {
            return "500" ;
        }
    }

    @Override
    @Transactional
    public String updateUserRefGen(String email) {
        try{
            Optional<User> optionalUser = userRepo.findByEmail(email);
            if (optionalUser.isEmpty()) return "404-1";
            ObjectId userId = optionalUser.get().getId();
            Optional<Referral> optionalReferral = referralRepo.findByUserId(userId);
            if (optionalReferral.isEmpty()) return "404-2";

            User user = optionalUser.get();
            user.setRefGen(optionalReferral.get().getRefcode().toString());

            userRepo.save(user) ;
            return user.getRefGen();
        }
        catch (Exception e)
        {
            return "500" ;
        }
    }
}
