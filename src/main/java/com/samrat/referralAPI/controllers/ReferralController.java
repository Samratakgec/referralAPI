package com.samrat.referralAPI.controllers;


import com.samrat.referralAPI.models.Referral;
import com.samrat.referralAPI.models.User;
import com.samrat.referralAPI.services.referral.ReferralInterface;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("/api/referral")
public class ReferralController {
    @Autowired
    ReferralInterface referralInterface ;


    @GetMapping("/track")
    public ResponseEntity<?> fetchSuccessAndPending ()
    {
        List<List<Optional<User>>> ans = referralInterface.fetchSuccessAndPending();
       return ResponseEntity.ok().body(ans) ;
       // return null ;
    }

    @GetMapping("/download")
    public void downloadCSV(HttpServletResponse response) throws IOException
    {
        List<Referral> referrals = referralInterface.downloadCSV();

        if (referrals.isEmpty()) {
            response.setContentType("text/plain");
            response.getWriter().write("No referrals found!");
            return;
        }

        // Set response headers to force download
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=referrals.csv");

        // Create CSV writer
        PrintWriter writer = response.getWriter();
        writer.println("ID,UserID,UsageCount,SuccessfulUsers,PendingUsers"); // Header row

        for (Referral referral : referrals) {
            String successfulUsers = String.join(";", referral.getSuccessfulUsers().toString());
            String pendingUsers = String.join(";", referral.getPendingUsers().toString());

            writer.println(referral.getRefcode().toString() + "," +
                    referral.getUserId().toString() + "," +
                    referral.getUsageCount() + "," +
                    successfulUsers + "," +
                    pendingUsers);
        }
        writer.flush();
        writer.close();

    }
}
