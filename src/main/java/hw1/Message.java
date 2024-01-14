package hw1;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Message {

	@JsonProperty("id")
	public String id;

	// hello, start, guess, retry.. bye error
	@JsonProperty("type")
	public String type;

	@JsonProperty("northeastern_username")
	public String username;

	@JsonProperty("word")
	public String word;

	
	// guesses: max 500
	@JsonProperty("guesses")
	public List<Guess> guesses;

	@JsonProperty("flag")
	public String flag;
	
	@JsonProperty("message")
	public String message;

}

class Guess {

	// exactly 5 characters long, and all words are lowercase
	// Guesses are case-sensitive
	@JsonProperty("word")
	public String word;

 
	// 0 Letter does not appear in the secret word
	// 1 Letter appears in the secret word, but not in this position
	// 2 Letter appears in the secret word in this position - dont change
	@JsonProperty("marks")
	public List<Integer> marks;

}