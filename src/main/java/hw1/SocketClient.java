package hw1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SocketClient {

	public static void main(String[] args) throws Exception {

		// $ ./client <-p port> <-s> <hostname> <username>

		int serverPort = 27993;

		// 27994
		boolean secure = false;

		// proj1.3700.network
		String hostname = null;
		String username = null;

		// Parse command-line arguments
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if (arg.equals("-p")) {
				// Check if there is a value after the option
				if (i + 1 < args.length) {
					serverPort = Integer.parseInt(args[i + 1]);
					i++; // Skip the next element since it's the value for -p
				}
			} else if (arg.equals("-s")) {
				secure = true;
			} else if (hostname == null) {
				hostname = arg;
			} else if (username == null) {
				username = args[i];
			} else {
				System.out.println("Unexpected argument: " + args[i]);
			}
		}

		Socket socket = null;
 
		int call = 0;

		if (secure) {
			try {
 
				SocketFactory factory = SSLSocketFactory.getDefault();
				socket = factory.createSocket(hostname, serverPort);
				System.out.println("Connected to the TLS server on " + hostname + ":" + serverPort);

			} catch (Exception ex) {
				System.out.println("Failed to establish TLS connection to server " + hostname + ":" + serverPort);
				System.exit(1);
			}

		} else {
			try {
				socket = new Socket(hostname, serverPort);
				System.out.println("Connected to the  server on " + hostname + ":" + serverPort);
			} catch (Exception e) {
				System.out.println("Failed to establish  connection to server " + hostname + ":" + serverPort);
				System.exit(1);
			}
		}

		 

			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
 
 
			ObjectMapper objectMapper = new ObjectMapper();

			objectMapper.setSerializationInclusion(Include.NON_NULL);
 

		   List<String> words = new LinkedList<>();

		   URL url = new URL("https://3700.network/projects/project1-words.txt");
		   BufferedReader in = new BufferedReader(
		   new InputStreamReader(url.openStream()));
   
		   String inputLine;
		   while ((inputLine = in.readLine()) != null)
			    words.add(inputLine); 
		   in.close();
	   
 
			Message init = new Message();
			init.type = "hello";
			init.username = username;

			String userInput = objectMapper.writeValueAsString(init);

			// Send JSON message to the server
			// ssl error if no match
			writer.println(userInput);

			String serverResponse = reader.readLine();

			// run once to get id
			System.out.println("Server response: " + serverResponse);
			Message message = objectMapper.readValue(serverResponse, Message.class);
			message.username = null;


			String last  = "";

			while (words.size() > 0) {
                
				last = words.get(0);

				message.word = last;
				message.type = "guess";
				try {
					writer.println(objectMapper.writeValueAsString(message));
					call++;
					serverResponse = reader.readLine();
					System.out.println("Server response: " + serverResponse);
					message = objectMapper.readValue(serverResponse, Message.class);
						 
				} catch ( Exception e) {
					e.printStackTrace();
				}
		

				if ("bye".equalsIgnoreCase(message.type)) {
					// save flag
					System.out.println("Guess it right: " + message.flag);
					System.out.println("secret : " + last);
					System.out.println("num of calls : " + call);
					System.exit(0);
				}
				words.remove(0);
               
				// if (message.flag != null) {
				// 	// save flag
				// 	System.out.println("Guess it right: " + message.flag);
				// 	System.exit(0);
				// }

				Guess guess = message.guesses.get(message.guesses.size() - 1);
				List<Integer> marks = guess.marks;

				// parse marks
				for (int j = 0; j < 5; j++) {
					// 0 remove all
				    final int idx = j ;
					final String sub = last.substring(idx, idx+1);
					// "aahed","marks":[1,0,1,0,0]}],
					// "aahed","marks":[0,2,0,0,0]}]
					// if (marks.get(j) == 0 && sb.indexOf(last.substring(j, j+1)) == -1)  {
						
					// 	words = words.stream().filter( e -> !e.contains(sub)).collect(Collectors.toList());
					// }  else  
					if (marks.get(j) == 1) {
						// keep if has it
						words = words.stream().filter( e -> e.contains(sub)).collect(Collectors.toList());
						// same location remove
						words = words.stream().filter( e -> !e.substring(idx, idx+1).equals(sub)).collect(Collectors.toList());
					}   else  if (marks.get(j) == 2) {
						// keep if eaxct match
						words = words.stream().filter( e ->  e.substring(idx, idx+1).equals(sub)).collect(Collectors.toList());
					}    
 
			  
			}
		}
 
	} 

}
