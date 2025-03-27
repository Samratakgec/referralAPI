package com.samrat.referralAPI.services.user;

import com.samrat.referralAPI.models.User;
import com.samrat.referralAPI.repositories.UserRepo;
import com.samrat.referralAPI.utils.UserSession;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements UserInterface {
    @Autowired
    UserSession userSession ;

    @Autowired
    private UserRepo userRepo;

//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder; // Password encoder bean

    @Override
    @Transactional
    public String createUser(User user) {
        try {
            // Check for existing user by email

            Optional<User> existingUser = userRepo.findByEmail(user.getEmail());
            if (existingUser.isPresent()) {
                return "409"; // User already exists
            }

            // Hash the password before saving
            //String hashedPassword = passwordEncoder.encode(user.getPassword());
            String hashedPassword = user.getPassword() ;
            user.setPassword(hashedPassword);

            // Save user in DB
            User savedUser = userRepo.save(user);
            // setting userId globally
            userSession.setUserObjectId(savedUser.getId());
            return "200"; // successfully saved
        } catch (Exception e) {
            return "500"; // Return 500 in case of an exception
        }
    }

    @Override
    public String loginUser (User user)
    {
        try {
          Optional<User> optionalUser =  userRepo.findByEmail(user.getEmail()) ;
          if (optionalUser.isPresent())
          {
              User fetchedUser = optionalUser.get() ;
              if (Objects.equals(fetchedUser.getPassword(), user.getPassword()))
              {
                  userSession.setUserObjectId(fetchedUser.getId());
                  return "200" ;
              }
              else return "403" ;
          }
          return  "404" ;
        }
        catch (Exception e)
        {
            return "500" ;
        }
    }
}
