package com.samrat.referralAPI.services.referral;

import com.samrat.referralAPI.models.Referral;
import com.samrat.referralAPI.models.User;
import com.samrat.referralAPI.repositories.ReferralRepo;
import com.samrat.referralAPI.repositories.UserRepo;
import com.samrat.referralAPI.utils.UserSession;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ReferralService implements ReferralInterface{
    @Autowired
    ReferralRepo referralRepo ;
    @Autowired
    UserSession userSession ;
    @Autowired
    UserRepo userRepo ;

    @Override
    @Transactional
    public String validateRefGain(String refgain) {
        try {
            Optional<Referral> referral = referralRepo.findById(new ObjectId(refgain));
            if(referral.isPresent())
            {
                return "200" ;
            }
            else return  "404" ;
        }
        catch (Exception e)
        {
            return "500";
        }
    }

    @Override
    @Transactional
    public String updateEntryForPending(String refgain) {
        try
        {
            Optional<Referral>referral = referralRepo.findById(new ObjectId(refgain)) ;
            ObjectId userId = userSession.getUserObjectId() ;
            if (referral.isPresent())
            {
                Referral existingReferral = referral.get() ;
                List<ObjectId> pendingUsers = existingReferral.getPendingUsers() ;
                pendingUsers.add(userId) ;
                existingReferral.setPendingUsers(pendingUsers);
                referralRepo.save(existingReferral) ;
                return  "200" ;
            }
            else return "404" ;
        }
        catch (Exception e)
        {
            return "500";
        }

    }

    @Override
    @Transactional
    public String createReferral() {
        final ObjectId userId = userSession.getUserObjectId() ;

        if (userId == null) return  null ;
        try {
           Optional<Referral> rfcheck = referralRepo.findByUserId(userId) ;
           if (rfcheck.isPresent()) return "409" ;

           Referral referral = new Referral() ;
           referral.setUserId(userId);
           referral.setUsageCount(0);
           referral.setSuccessfulUsers(new ArrayList<>());
           referral.setPendingUsers(new ArrayList<>());


           referralRepo.save(referral) ;

            Optional<User> user = userRepo.findById(userId) ;
           if (user.isPresent())
           {
               User existingUser = user.get() ;
               existingUser.setRefGen(referral.getRefcode().toString());
               userRepo.save(existingUser) ;
           }
           else
           {
               return "404" ;
           }

           return "200" ;
        }
        catch (Exception e)
        {
            return "500";
        }
    }

    @Override
    @Transactional
    public String updateEntryForSuccessfulAndRemoveFromPending() {
        final ObjectId userId = userSession.getUserObjectId() ;
        if (userId == null) return  null ;
        try
        {
            String refId = userRepo.findById(userId).get().getRefGain() ;
            if (refId == null) return "200";
            Optional<Referral> referral = referralRepo.findById(new ObjectId(refId));
            if (referral.isEmpty()) return "404" ;
            Referral existingReferral = referral.get() ;
            List<ObjectId>successfulUsers = existingReferral.getSuccessfulUsers();
            List<ObjectId>pendingUsers = existingReferral.getPendingUsers() ;
            successfulUsers.add (userId) ;
            pendingUsers.remove(userId) ;
            existingReferral.setUsageCount(existingReferral.getUsageCount()+1);
            existingReferral.setSuccessfulUsers(successfulUsers);
            existingReferral.setPendingUsers(pendingUsers);
            referralRepo.save(existingReferral) ;
            return "200";
        }
        catch (Exception e)
        {
            return "500";
        }

    }

    @Override
    @Transactional
    public List<List<Optional<User>>>  fetchSuccessAndPending() {
        final ObjectId userId = userSession.getUserObjectId() ;
        if (userId == null) return  null ;
        Optional<Referral> reff = referralRepo.findByUserId(userId) ;
        if (reff.isEmpty()) return null ;
        Referral referral = reff.get() ;

        List<ObjectId> pendingUsersIds = referral.getPendingUsers() ;
        List<ObjectId> successfulIds = referral.getSuccessfulUsers() ;
        List<Optional<User>> pendingUsers = new ArrayList<>() ;
        List<Optional<User>> successfulUsers = new ArrayList<>() ;

        pendingUsersIds.forEach(id ->pendingUsers.add(userRepo.findById(id)));
        successfulIds.forEach(id -> successfulUsers.add(userRepo.findById(id)));
        List<List<Optional<User>>> ans = new ArrayList<>();
        ans.add(pendingUsers) ;
        ans.add(successfulUsers);
        return ans;
    }

    @Override
    @Transactional
    public List<Referral> downloadCSV() {
        return Optional.of(referralRepo.findAll()).orElse(Collections.emptyList());
    }
}
