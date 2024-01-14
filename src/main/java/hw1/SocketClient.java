package hw1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SocketClient {

	public static void main(String[] args) {

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

		if (secure) {
			try {
				// Load the truststore (contains the server's public key)
				char[] truststorePassword = "password".toCharArray();
				KeyStore truststore = KeyStore.getInstance("JKS");
				truststore.load(new FileInputStream("keystore.jks"), truststorePassword);

				// Set up the trust manager factory
				TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
				trustManagerFactory.init(truststore);

				// Set up the SSL context
				SSLContext sslContext = SSLContext.getInstance("TLS");
				sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());

				// Create SSLSocketFactory
				SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

				// Create SSLSocket
				socket = sslSocketFactory.createSocket(hostname, serverPort);

				System.out.println("Connected to the TLS server on " + hostname + ":" + serverPort);

			} catch (Exception ex) {

				ex.printStackTrace();
			}

		} else {
			try {
				socket = new Socket(hostname, serverPort);
				System.out.println("Connected to the  server on " + hostname + ":" + serverPort);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

			String[] candidates = new String[5];

			String[] spool = new String[] { "abcde", "fghij", "klmno", "pqrst", "uvwxy", "zzzzz" };

			ObjectMapper objectMapper = new ObjectMapper();

			objectMapper.setSerializationInclusion(Include.NON_NULL);

			// manual
//                System.out.print("Enter a JSON message (or 'exit' to quit): ");               
//                String userInput = consoleReader.readLine();
//                
//                if ("exit".equalsIgnoreCase(userInput.trim())) {
//                    System.out.println("quit ");
//                    System.exit(0);
//                	
//                }

			Message init = new Message();
			init.type = "hello";
			init.username = username;

			String userInput = objectMapper.writeValueAsString(init);

			// Send JSON message to the server
			// ssl error if no match
			writer.println(userInput);
			String serverResponse = reader.readLine();

			System.out.println("Server response: " + serverResponse);
			Message message = objectMapper.readValue(serverResponse, Message.class);
			message.username = null;

			while (true) {

				// {"type": "hello", "northeastern_username": "alex"}

				// {"id":"389f23db-42e6-4261-8f35-2a5de0a5b277","type":"guess", "word": "treat"}
				// {"id":"389f23db-42e6-4261-8f35-2a5de0a5b277","type":"guess","word":"sweat","guesses":[{"word":"treat","marks":[1,0,2,2,1]}]}
				// {"id":"389f23db-42e6-4261-8f35-2a5de0a5b277","type":"guess","word":"steam","guesses":[{"word":"treat","marks":[1,0,2,2,1]},{"word":"sweat","marks":[2,0,2,2,1]}]}

				// steam {"id":"389f23db-42e6-4261-8f35-2a5de0a5b277","type":"guess", "word":
				// "steam"}
				// treat

				// a-z
				// try all, remove 0, keep 2, shuffle 1
				// abcde fghij klmno pqrst uvwxy zzzzz: get all chars 1/2, try all same -> 2
				// max 6 + 6: 12 times
				// dup: get 2+ 2

				// loop 1s

				// guess n mark - replace non-2 with next char

				if ("start".equalsIgnoreCase(message.type)) {
					// save flag
					// System.out.println("Guess it right: " + message.flag);
					// System.out.println("Secret: " + message.flag);
					// {"type": "start", "id": "foo"}\n

					StringBuilder sb = new StringBuilder();

					for (int i = 0; i < 6; i++) {
						message.word = spool[i];
						message.type = "guess";
						writer.println(objectMapper.writeValueAsString(message));
						serverResponse = reader.readLine();
						message = objectMapper.readValue(serverResponse, Message.class);

						System.out.println("Server response: " + serverResponse);

						if (message.flag != null) {
							// save flag
							System.out.println("Guess it right: " + message.flag);
							System.exit(0);
						}

						// get 2/1

						Guess last = message.guesses.get(message.guesses.size() - 1);
						List<Integer> marks = last.marks;

						// parse marks
						for (int j = 0; j < 5; j++) {
							if (marks.get(j) != 0) {
								sb.append(spool[i].charAt(j));
							}

						}

						// max 5
						if (sb.length() == 5) {
							break;
						}

					}

					// now we have all chars
					for (int i = 0; i < 5; i++) {
						candidates[i] = sb.toString();
					}

				} else if ("retry".equalsIgnoreCase(message.type)) {
					// aemst: get first char from candidates
					// 2 - remove rest

					// at most 5 times
					for (int i = 0; i < 5; i++) {

						StringBuilder sb = new StringBuilder();
						for (int j = 0; j < 5; j++) {
							try {
								sb.append(candidates[j].charAt(0));
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						message.word = sb.toString();
						message.type = "guess";
						writer.println(objectMapper.writeValueAsString(message));
						serverResponse = reader.readLine();
						System.out.println("Server response: " + serverResponse);

						message = objectMapper.readValue(serverResponse, Message.class);

						if ("bye".equalsIgnoreCase(message.type)) {
							// save flag
							System.out.println("Guess it right: " + message.flag);
							System.exit(0);
						}

						// get 2/1
						Guess last = message.guesses.get(message.guesses.size() - 1);
						List<Integer> marks = last.marks;

						// parse marks: 2 or 1
						for (int z = 0; z < 5; z++) {
							// 2 remove rest, 1 more head
							if (marks.get(z) == 2) {
								candidates[z] = candidates[z].substring(0, 1);
							} else {
								candidates[z] = candidates[z].substring(1);
							}

						}

					}

				} else if ("error".equalsIgnoreCase(message.type)) {
					System.out.println("system error: " + message.message);
					System.exit(1);

				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
