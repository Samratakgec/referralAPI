package com.samrat.referralAPI.services.referral;

import com.samrat.referralAPI.models.Referral;
import com.samrat.referralAPI.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ReferralInterface {
    String validateRefGain (String refgain) ;
    String updateEntryForPending (String refgain, String email) ;
    String createReferral (String email) ;
    String updateEntryForSuccessfulAndRemoveFromPending (String email) ;
    List<List<Optional<User>>>  fetchSuccessAndPending (String email) ;
    List<Referral> downloadCSV () ;

}
