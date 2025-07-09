package com.example.mongoDB.Controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.mongoDB.Entity.User;
import com.example.mongoDB.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mongoDB.Entity.JournalEntry;
import com.example.mongoDB.service.JournalService;

@RestController
@RequestMapping("/journal")
public class JournalController {
	
	@Autowired
	public JournalService journalService;

	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry) {
		try{
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			journalService.saveEntry(journalEntry,authentication.getName());
			return new ResponseEntity<>(journalEntry,HttpStatus.CREATED);
		}catch (Exception e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
	
	@GetMapping
	public ResponseEntity<?> getAllJournalEntrisOfUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUserName(authentication.getName());
		List<JournalEntry> all = user.getJournalEntries();
		if(all != null && !all.isEmpty()){
			return new ResponseEntity<>(all, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("id/{myId}")
	public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user = userService.findByUserName(username);
		List<JournalEntry> collect = user.getJournalEntries().stream().filter(x ->  x.getId().equals(myId)).toList();
		if(!collect.isEmpty()){
			Optional<JournalEntry> journalEntry = journalService.findById(myId);
			if(journalEntry.isPresent()){
				return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
			}
		}
			return new ResponseEntity<>("Journal not found",HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("id/{myId}")
	public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		boolean response = journalService.deleteById(myId,userName);
		if(response){
			return new ResponseEntity<>("Journal Deleted Successfully",HttpStatus.OK);
		}else{
			return new ResponseEntity<>("Journal not deleted or does not exist",HttpStatus.NOT_FOUND);
		}
	}



	
	@PutMapping("id/{myId}")
	public ResponseEntity<?> updateJournalById(@PathVariable ObjectId myId,
									 @RequestBody JournalEntry newEntry) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		User user = userService.findByUserName(userName);
		List<JournalEntry> collect = user.getJournalEntries().stream().filter(x ->  x.getId().equals(myId)).toList();

		if(!collect.isEmpty()){
			Optional<JournalEntry> journalEntry = journalService.findById(myId);
			if(journalEntry.isPresent()){
				JournalEntry old = journalEntry.get();
				old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
				old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
				journalService.saveEntry(old);
				return  new ResponseEntity<>("Journal updated successfully",HttpStatus.OK);
			}
		}
		return new ResponseEntity<>("Journal not updated",HttpStatus.NOT_FOUND);
	}
	
}
