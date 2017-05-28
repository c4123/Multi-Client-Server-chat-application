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
import java.util.ArrayList;
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
	private boolean loginSuccess;

	public clientThread(Socket clientSocket, clientThread[] threads) {
		this.clientSocket = clientSocket;
		this.threads = threads;
		maxClientsCount = threads.length;
	}

	public void serverMsg(String text){
		Data msgData = new Data(Constants.TYPE_MSG,text,null);
		msgData.setSendor("DonggukBot");
		try {
			os.writeObject(msgData);
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
			/*
			 * Login Process 
			 */
			while (!loginSuccess) {
				DBHelper.getConnection();
				LoginData data = (LoginData)is.readObject();
				String id = data.getId();
				System.out.println(id+" 로그인 시도");
				int dataType = data.getType();
				if(dataType == Constants.TYPE_REGISTER) {
					
				}
				else if(dataType == Constants.TYPE_LOGIN) {
					boolean found = DBHelper.getIdCheck(id);
					
					if(found) {
						loginSuccess = true;
						serverMsg(Constants.LOGIN_SUCCESS);
						serverMsg("enter your nickname");
						
						Data receiveData = (Data)is.readObject();
						clientName =receiveData.getMsg();
						user = new User(data.getId(), clientName);
					}
					else {
						serverMsg(Constants.LOGIN_FAILED);
					}
					
				}
			}
			//로그인 성공한 것이므로  현재 CurrentUser에 지금 User추가하고 갱신된 리스트 뿌려주기
		
			MultiThreadChatServerSync.currentUser.add(user);
			ArrayList<User> nowCurrentUser= new ArrayList<>();
			nowCurrentUser.addAll(MultiThreadChatServerSync.currentUser);
			Data data= new Data(Constants.TYPE_USER_LIST, "", nowCurrentUser);
			
			synchronized (this) {
			for(int i=0;i<maxClientsCount;i++){
					if (threads[i] != null && threads[i].loginSuccess) { //로그인 성공한 사람한테만 보여줌
						threads[i].os.writeObject(data);
						threads[i].os.flush();
					}
				}
			}
			
			//로그인  성공한 사람에 대한 환영메세지
			/* Welcome the new the client. */
			serverMsg("Welcome " + user.getId() + "("+user.getNickname()+") to our chat room.\nTo leave enter /quit in a new line.");
			
			synchronized (this) {
				for (int i = 0; i < maxClientsCount; i++) {
					if (threads[i] != null && threads[i] != this && threads[i].loginSuccess) { //로그인 성공한 사람한테만 보여줌
						String welcomeMsg = "*** A new user " +user.getId() + "("+user.getNickname()+") entered the chat room !!! ***";
						Data welcomeData = new Data(Constants.TYPE_MSG,welcomeMsg,null);
						welcomeData.setSendor("DonggukBot");
						threads[i].os.writeObject(welcomeData);
						threads[i].os.flush();
					}
				}
			}
			
			/* Start the conversation. */
			while (loginSuccess) {
					//클라이언트의 작은 서버가 클라로부터 data받았을때 처리 루틴
					Data receiveData = (Data)is.readObject();
					int type = receiveData.getType();
					receiveData.setSendor(clientName);
					//보내는 사람을 현재 계정으로 설정
					if(type==Constants.TYPE_MSG){
						synchronized (this) {
							for (int i = 0; i < maxClientsCount; i++) {
								if (threads[i] != null && threads[i].clientName != null && threads[i].loginSuccess) {

									threads[i].os.writeObject(receiveData);
									threads[i].os.flush();
								}
							}
						}
					}
					else if(type ==Constants.TYPE_WHISPER){
						synchronized (this) {
							for (int i = 0; i < maxClientsCount; i++) {
								if (threads[i] != null && threads[i] != this && threads[i].clientName != null
										&& threads[i].clientName.equals(receiveData.getReceiver()) && threads[i].loginSuccess) {
									threads[i].os.writeObject(receiveData);
									threads[i].os.flush();
							
									/*
									 * Echo this message to let the client
									 * know the private message was sent.
									 */
									break;
								}
							}
						}
					}
					else if(type == Constants.TYPE_QUIT){
						loginSuccess = false;
					}
			}

			/*
			 * Clean up. Set the current thread variable to null so that a new
			 * client could be accepted by the server.
			 */
			
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
