package com.example.mongoDB.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.mongoDB.Entity.User;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.mongoDB.Entity.JournalEntry;
import com.example.mongoDB.Repository.JournalRepo;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class JournalService {
	
	@Autowired
	JournalRepo journalRepo;

	@Autowired
	UserService userService;


	@Transactional
	public void saveEntry(JournalEntry journalEntry, String userName) {
		try{
			User user = userService.findByUserName(userName);
			journalEntry.setDate(LocalDateTime.now());
			JournalEntry saved = journalRepo.save(journalEntry);
			user.getJournalEntries().add(saved);
			
			userService.saveEntry(user);
		} catch (Exception e) {
			log.error("Exception ",e);
			throw new RuntimeException("An error occured while saving the entry.",e);
		}

	}

	public void saveEntry(JournalEntry journalEntry) {
		try{

			journalEntry.setDate(LocalDateTime.now());
			journalRepo.save(journalEntry);

		} catch (Exception e) {
			log.error("Exception ",e);
		}

	}
	
	public List<JournalEntry> getAll(){
		return journalRepo.findAll();
	}

	public Optional<JournalEntry> findById(ObjectId myId) {
		
		return journalRepo.findById(myId);
	}

	@Transactional
	public boolean deleteById(ObjectId myId, String userName) {
		boolean removed = false;
		try {
			User user = userService.findByUserName(userName);
			removed = user.getJournalEntries().removeIf(x -> x.getId().equals(myId));
			if (removed) {
				userService.saveEntry(user);
				journalRepo.deleteById(myId);
			}
		}catch (Exception e){
			System.out.println(e);
			throw new RuntimeException("An error occured while saving the entry",e);
		}
		return removed;
	}

	public void updateById(ObjectId myId, JournalEntry journalEntry) {
		
		
	}


	

}
