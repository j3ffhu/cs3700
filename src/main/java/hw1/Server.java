package hw1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Server {

	public static void main(String[] args) {

		final int portNumber = 27994;
		
		 ServerSocket serverSocket = null;
 
		try {
			
	        // Create SSLServerSocket
			SSLServerSocketFactory sslServerSocketFactory;
			
			char[] keystorePassword = "password".toCharArray();
			KeyStore keyStore = KeyStore.getInstance("JKS");
			
			// keytool -genkeypair -keyalg RSA -keysize 2048 -keystore yourkeystore.jks -alias youralias
			keyStore.load(new FileInputStream("keystore.jks"), keystorePassword);

			// Set up the key manager factory
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
			keyManagerFactory.init(keyStore, keystorePassword);

			// Set up the trust manager factory
			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
			trustManagerFactory.init(keyStore);

			// Set up the SSL context
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());

			sslServerSocketFactory = sslContext.getServerSocketFactory();
			
		    serverSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(portNumber) ;
		    System.out.println("Server is listening on TLS port " + portNumber);
			
		}  catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
     
 
	 
			

			while (true) {
				
				try   {
					
					Socket clientSocket = serverSocket.accept();
					BufferedReader reader = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));
					PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()),true);

					System.out.println("Client connected from " + clientSocket.getInetAddress());
					
				 	String secret = "steam";
			   	//	String secret = "abcde";

					ObjectMapper objectMapper = new ObjectMapper();
					
					objectMapper.setSerializationInclusion(Include.NON_NULL);

					String inputLine;
					while ((inputLine = reader.readLine()) != null) {
						System.out.println("Received from client: " + inputLine);

						// Use ObjectMapper to bind JSON to MyData object

						Message message = null;
						
						  try {
							message = objectMapper.readValue(inputLine, Message.class);
						} catch (Exception e) {
						 
							e.printStackTrace();
							
							message = new Message( );
							message.message = "invalid input";
							message.type = "error";
	                    	writer.println(objectMapper.writeValueAsString(message));
	                    	break;
							
						}


						// process n return
 
						if (message.type.equalsIgnoreCase("hello")) {
					        UUID uuid = UUID.randomUUID();
				 
					        
					        message.id = uuid.toString();
					        message.type = "start";
					        message.username = null;
							
						} else if (message.type.equalsIgnoreCase("guess")) {
							// compare to secret 	public List<Integer> marks; 	public String word;
							
							
							
							String word = message.word;
							
		                    // S -> C: {"type": "bye", "id": "foo", "flag": "sndk83nb5ks&*dk*SKDFHGk"}\n
		                       if (word.equals(secret)) {
		                    	   message.type = "bye";
		                    	   message.guesses  = null;
		                    	   message.flag = "sndk83nb5ks";
		                    	   writer.println(objectMapper.writeValueAsString(message));
		                    	   break;
		                    	   
		                       }
		                       
		               		if (message.guesses == null) {
								message.guesses = new LinkedList<Guess>(); 
 	
							}  
		               		
							Guess guess = new Guess();
							guess.word = word;
							guess.marks = getMasks(word, secret);
							
							message.type = "retry";
									
					
							if (word.equals("daddd")) {
								
								System.out.println("word: " + word);
							}
								
	                       message.guesses.add(guess);
	                       // {"id":"389f23db-42e6-4261-8f35-2a5de0a5b277","type":"guess","word":"steam","guesses":[{"word":"treat","marks":[1,0,2,2,1]},{"word":"sweat","marks":[2,0,2,2,1]}]}
	                       
	   
	 			 
							 
						}
						
						String jsonString = objectMapper.writeValueAsString(message);
					//	System.out.println("Sent to client: " + jsonString);
						writer.println(jsonString);

					}

					System.out.println("Client disconnected");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		 
	 

	}
	
	public static List<Integer> getMasks(String word, String secret) {
		
		List<Integer> marks = new LinkedList<Integer>(); ;
		
		for (int i = 0; i <5; i++) {
			if (word.charAt(i) == secret.charAt(i) )  {
				marks.add(2) ;
			} else if (secret.indexOf(word.charAt(i)) > -1)  {
				marks.add(1) ;
			} else {
				marks.add(0) ;
			}
		}
		
		
		return marks;
	}
}
