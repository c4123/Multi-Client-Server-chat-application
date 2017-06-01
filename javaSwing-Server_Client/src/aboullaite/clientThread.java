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
		msgData.setSendor(Constants.DonggukBotName);
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
			//서버에 접속되었다는 것을 클라이언트에게 알려줌
			serverMsg(Constants.SERVER_CONNECTED);
			
			while (!loginSuccess) {
				DBHelper.getConnection();
				LoginData data = (LoginData)is.readObject();
				String id = data.getId();
				int dataType = data.getType();
				if(dataType == Constants.TYPE_REGISTER) {
					System.out.println(id+" 회원가입 시도");
					//회원가입 진행
					
					String authCode = Utils.generateNumber(6);	//6자리 인증코드 만들기
					if(DBHelper.getIdCheck(data.getId())){
						//ID가 존재하면
						System.out.println("아이디 없음");
						serverMsg(Constants.REGISTER_FAIL_ID);
					}
					else{
						//ID가 존재하지 않으면
						System.out.println("인증메일 보내는 중");
						Utils.sendMail(data.getId(), authCode); //인증메일 보내기
						serverMsg(Constants.REGISTER_WAITING_AUTHCODE);
						
						//여기에 무한정 기다리게 할 수 없으니 타이머 추가해야할거 같은데.
						String msgFromClient =((Data)is.readObject()).getMsg(); //클라이언트가 보내온 인증코드.
						
						 //인증코드 일치할때
						if(msgFromClient.equals(Constants.REGISTER_CANCEL)){
							//사용자가 취소를 누른 상황이면
							System.out.println("회원 인증 취소");
							continue;//while문으로 돌아간다.
						}
						else{
							 if(msgFromClient.equals(authCode)){
								//회원가입 진행
									DBHelper.insertUser(data.getId(), data.getPasswd());
									serverMsg(Constants.REGISTER_SUCCESS);
							 }
							 else{
								 //회원가입 실패
								 serverMsg(Constants.REGISTER_FAIL_AUTHCODE);
							 }
							
						}

					}
				
					
				}
				else if(dataType == Constants.TYPE_LOGIN) {
					boolean found = DBHelper.getUserCheck(data.getId(), data.getPasswd());
					System.out.println(id+" 로그인 시도");
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
		
			synchronized (this) {
				MultiThreadChatServerSync.currentUser.add(user);
				ArrayList<User> nowCurrentUser= new ArrayList<>();
				nowCurrentUser.addAll(MultiThreadChatServerSync.currentUser);
				Data data= new Data(Constants.TYPE_USER_LIST, "", nowCurrentUser);
				for(int i=0;i<maxClientsCount;i++){
					if (threads[i] != null && threads[i].loginSuccess) { //로그인 성공한 사람한테만 보여줌
						threads[i].os.writeObject(data);
						threads[i].os.flush();
					}
				}
			}
			
			//로그인  성공한 사람에 대한 환영메세지
			/* Welcome the new the client. */
			serverMsg("Welcome " + user.getId() + "("+user.getNickname()+") to our chat room.");
			
			synchronized (this) {
				for (int i = 0; i < maxClientsCount; i++) {
					if (threads[i] != null && threads[i] != this && threads[i].loginSuccess) { //로그인 성공한 사람한테만 보여줌
						String welcomeMsg = "*** A new user " +user.getId() + "("+user.getNickname()+") entered the chat room !!! ***";
						Data welcomeData = new Data(Constants.TYPE_MSG,welcomeMsg,null);
						welcomeData.setSendor(Constants.DonggukBotName);
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
					
					//일반 메시지 타입이나, 공지사항 갱신 요청일 경우 모두에게 보낸다.
					if(type==Constants.TYPE_MSG || type ==Constants.TYPE_NOTIFICATION){
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
						System.out.println(user.getId()+" QUIT 요청보냄");
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
		      synchronized (this) {
		          for (int i = 0; i < maxClientsCount; i++) {
		            if (threads[i] != null && threads[i] != this
		                && threads[i].clientName != null) {
		            	Data data = new Data(Constants.TYPE_MSG,"*** The user " + user.getNickname()+"("
				                 +user.getId() + ") is leaving the chat room !!! ***",null);
		            	data.setSendor(Constants.DonggukBotName);
		              threads[i].os.writeObject(data);
		              threads[i].os.flush();
		            }
		          }
		        }
		      

		        /*
		         * Clean up. Set the current thread variable to null so that a new client
		         * could be accepted by the server.
		         */
		      
		      //종료 과정 : 현재 스레드를 null로 만들어 준다.
		        synchronized (this) {
		          for (int i = 0; i < maxClientsCount; i++) {
		            if (threads[i] == this) {
		              threads[i] = null;
		            }
		          }
		          
		      //종료과정 2: currentUser 전역변수의 나 자신을 빼고, 반영된 currentUser를 모두에게 뿌려준다.  
		          for(int i=0;i<MultiThreadChatServerSync.currentUser.size();i++)
		          {
		        	  if(MultiThreadChatServerSync.currentUser.get(i).getId().equals(user.getId())){
		       
		        		  MultiThreadChatServerSync.currentUser.remove(i); 	
		    			  ArrayList<User> nowCurrentUser= new ArrayList<>();
		    			  nowCurrentUser.addAll(MultiThreadChatServerSync.currentUser);
		    			  Data data= new Data(Constants.TYPE_USER_LIST, "", nowCurrentUser);
		    			  for(int j=0;j<maxClientsCount;j++){
		    				  if (threads[j] != null && threads[j].loginSuccess) { //로그인 성공한 사람한테만 보여줌
		    					  threads[j].os.writeObject(data);
		    					  threads[j].os.flush();
		    				  }
		    			  }
		    			  break;
		        	  }
		        	  
		          }
		        }
			is.close();
			os.close();
			clientSocket.close();
		} catch (IOException | ClassNotFoundException e) {
		}
	}
}
