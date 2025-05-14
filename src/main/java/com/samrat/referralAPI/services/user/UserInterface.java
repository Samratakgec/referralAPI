package com.samrat.referralAPI.services.user;

import com.samrat.referralAPI.models.LogInPayload;
import com.samrat.referralAPI.models.SignUpPayload;
import com.samrat.referralAPI.models.User;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface UserInterface {
    String createUser (SignUpPayload signUpPayload) ;

    String loginUser (LogInPayload logInPayload) ;

    String updateUserRefGen (String email) ;
}
