package hw1;

import java.io.*;
import java.net.*;

import com.fasterxml.jackson.databind.ObjectMapper;



public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int serverPort = 8080;
		Socket socket = null;
		ObjectOutputStream toServer = null;
		ObjectInputStream fromServer = null;
		
		
		
		
		try {
 
	 
			InetAddress serverHost = InetAddress.getByName("localhost"); 
			System.out.println("Connecting to server on port " + serverPort); 
			socket = new Socket(serverHost,serverPort); 
			System.out.println("Just connected to " + socket.getRemoteSocketAddress()); 
			toServer = new ObjectOutputStream(
					new BufferedOutputStream(socket.getOutputStream()));
			
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			Message msgToSend = new Message( );
			
			msgToSend.id = "123";
			
			String jsonString = objectMapper.writeValueAsString(msgToSend);
			
			toServer.writeObject(jsonString);
			
		//	toServer.flush();
			
			// This will block until the corresponding ObjectOutputStream 
			// in the server has written an object and flushed the header
			fromServer = new ObjectInputStream(
					new BufferedInputStream(socket.getInputStream()));
			
			String msgFromReply = (String) fromServer.readObject();
			System.out.println("reply:  " + msgFromReply); 
 
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		finally {
			if(socket != null) {
				try {
					socket.close();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	 

}
