package com.samrat.referralAPI.services.user;

import com.samrat.referralAPI.models.User;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
public interface UserInterface {
    String createUser (User user) ;
    String loginUser (User user) ;
}
