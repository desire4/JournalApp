package com.example.mongoDB.scheduler;

import com.example.mongoDB.Entity.JournalEntry;
import com.example.mongoDB.Entity.User;
import com.example.mongoDB.Repository.UserRepoImpl;
import com.example.mongoDB.enums.Sentiment;
import com.example.mongoDB.service.EmailService;
import com.example.mongoDB.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private UserRepoImpl userRepoImpl;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

//    @Scheduled(cron = "0 9 * * SUN")
//    @Scheduled(cron = "0 * * ? * *")
    public void fetchUserAndSendSaMail(){
        List<User> users = userRepoImpl.getUserForSA();
        for(User user:users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getSentiment()).collect(Collectors.toList());

            Map<Sentiment,Integer> sentimentCounts = new HashMap<>();
            for(Sentiment sentiment: sentiments){
               if(sentiment != null){
                   sentimentCounts.put(sentiment,sentimentCounts.getOrDefault(sentiment,0)+1);
               }
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for(Map.Entry<Sentiment,Integer> entry : sentimentCounts.entrySet()){
                if(entry.getValue() > maxCount){
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if(mostFrequentSentiment != null){
                emailService.sendEmail(user.getEmail(),"Sentiment for 7 days",mostFrequentSentiment.toString());
            }


        }

    }
}
