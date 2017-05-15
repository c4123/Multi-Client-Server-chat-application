/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aboullaite;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
/**
 *
 * @author mohammed
 */

// For every client's connection we call this class
public class clientThread extends Thread {
	private String clientName = null;
	private ObjectInputStream is = null;
	private ObjectOutputStream os = null;
	private Socket clientSocket = null;
	private final clientThread[] threads;
	private int maxClientsCount;
	private User user;
	boolean loginSuccess;

	public clientThread(Socket clientSocket, clientThread[] threads) {
		this.clientSocket = clientSocket;
		this.threads = threads;
		maxClientsCount = threads.length;
	}

	public void run() {
		int maxClientsCount = this.maxClientsCount;
		clientThread[] threads = this.threads;
		loginSuccess = false;
		try {
			/*
			 * Create input and output streams for this client.
			 */
			is = new ObjectInputStream(clientSocket.getInputStream());
			os = new ObjectOutputStream(clientSocket.getOutputStream());
			String nickname="";
			
			/*
			 * Login Process 
			 */
			while (!loginSuccess) {
				LoginData data = (LoginData)is.readObject();	

				if(data.getId().equals("test@dongguk.edu")){ //로긴 안되는 아이디 테스트용
					loginSuccess =false;
					os.writeUTF("no");
					os.flush();
				}
				else {
					//DB검사 로직 넣어야함
					//지금은 모든경우 로그인 되게 해놓았음. 
					loginSuccess = true;
					os.writeUTF("ok");
					os.writeUTF("enter your nickname");
					os.flush();		
					nickname = is.readUTF().trim();
					user = new User(data.getId(), nickname, clientSocket);
					//나중에 user추가하기.
				}
			}
			
			//로그인  성공한 사람에 대한 환영메세지
			/* Welcome the new the client. */
			os.writeUTF("Welcome " + user.getId() + "("+user.getNickname()+") to our chat room.\nTo leave enter /quit in a new line.");
			os.flush();
			
			
			synchronized (this) {
				for (int i = 0; i < maxClientsCount; i++) {
					if (threads[i] != null && threads[i] == this ) {
						clientName = "@"+ user.getNickname();
						break;
					}
				}
				for (int i = 0; i < maxClientsCount; i++) {
					if (threads[i] != null && threads[i] != this && threads[i].loginSuccess) { //로그인 성공한 사람한테만 보여줌
						threads[i].os.writeUTF("*** A new user " +user.getId() + "("+user.getNickname()+") entered the chat room !!! ***");
						threads[i].os.flush();
					}
				}
			}
			/* Start the conversation. */
			while (true) {
				String line = is.readUTF();
				if (line.startsWith("/quit")) {
					break;
				}
				/* If the message is private sent it to the given client. */
				if (line.startsWith("@")) {
					String[] words = line.split("\\s", 2);
					if (words.length > 1 && words[1] != null) {
						words[1] = words[1].trim();
						if (!words[1].isEmpty()) {
							synchronized (this) {
								for (int i = 0; i < maxClientsCount; i++) {
									if (threads[i] != null && threads[i] != this && threads[i].clientName != null
											&& threads[i].clientName.equals(words[0])) {
										
										threads[i].os.writeUTF("<" + nickname + "> " + words[1]);
										threads[i].os.flush();
										/*
										 * Echo this message to let the client
										 * know the private message was sent.
										 */
										this.os.writeUTF(">" + nickname + "> " + words[1]);
										this.os.flush();
										break;
									}
								}
							}
						}
					}
				/*
				 *  Show current user info
				 */
				} else if(line.startsWith("/user")) {
					synchronized (this) {
						this.os.writeUTF("Current user Info");
						for(int j = 0; j < maxClientsCount; j++) {
							if(threads[j] != null && threads[j].clientName != null && threads[j].loginSuccess)
								this.os.writeUTF(threads[j].clientName + " InetAddress : "+threads[j].clientSocket.getInetAddress());
								this.os.flush();
						}
					}
				} else {
					/*
					 * The message is public, broadcast it to all other clients.
					 */
					synchronized (this) {
						for (int i = 0; i < maxClientsCount; i++) {
							if (threads[i] != null && threads[i].clientName != null && threads[i].loginSuccess) {
								threads[i].os.writeUTF("<" + nickname + "> " + line);
								threads[i].os.flush();
							}
						}
					}
				}
			}
			synchronized (this) {
				for (int i = 0; i < maxClientsCount; i++) {
					if (threads[i] != null && threads[i] != this && threads[i].clientName != null && threads[i].loginSuccess) {
						threads[i].os.writeUTF("*** The user " + nickname + " is leaving the chat room !!! ***");
						threads[i].os.flush();
					}
				}
			}
			os.writeUTF("*** Bye " + nickname + " ***");
			os.flush();

			/*
			 * Clean up. Set the current thread variable to null so that a new
			 * client could be accepted by the server.
			 */
			synchronized (this) {
				for (int i = 0; i < maxClientsCount; i++) {
					if (threads[i] == this) {
						threads[i] = null;
					}
				}
			}
			/*
			 * Close the output stream, close the input stream, close the
			 * socket.
			 */
			is.close();
			os.close();
			clientSocket.close();
		} catch (IOException | ClassNotFoundException e) {
		}
	}
}
