package com.example.mongoDB.Entity;

import java.time.LocalDateTime;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "journal_entries")
@Data
@NoArgsConstructor //required for deserialization like json to pojo
public class JournalEntry {
	
	@Id
	private ObjectId id;

	@NonNull
	private String title;
	
	private String content;
	
	private LocalDateTime date;

	


	
	
	
}
