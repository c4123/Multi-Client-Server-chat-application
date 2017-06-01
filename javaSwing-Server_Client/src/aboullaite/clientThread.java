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
			//������ ���ӵǾ��ٴ� ���� Ŭ���̾�Ʈ���� �˷���
			serverMsg(Constants.SERVER_CONNECTED);
			
			while (!loginSuccess) {
				DBHelper.getConnection();
				LoginData data = (LoginData)is.readObject();
				String id = data.getId();
				int dataType = data.getType();
				if(dataType == Constants.TYPE_REGISTER) {
					System.out.println(id+" ȸ������ �õ�");
					//ȸ������ ����
					
					String authCode = Utils.generateNumber(6);	//6�ڸ� �����ڵ� �����
					if(DBHelper.getIdCheck(data.getId())){
						//ID�� �����ϸ�
						System.out.println("���̵� ����");
						serverMsg(Constants.REGISTER_FAIL_ID);
					}
					else{
						//ID�� �������� ������
						System.out.println("�������� ������ ��");
						Utils.sendMail(data.getId(), authCode); //�������� ������
						serverMsg(Constants.REGISTER_WAITING_AUTHCODE);
						
						//���⿡ ������ ��ٸ��� �� �� ������ Ÿ�̸� �߰��ؾ��Ұ� ������.
						String msgFromClient =((Data)is.readObject()).getMsg(); //Ŭ���̾�Ʈ�� ������ �����ڵ�.
						
						 //�����ڵ� ��ġ�Ҷ�
						if(msgFromClient.equals(Constants.REGISTER_CANCEL)){
							//����ڰ� ��Ҹ� ���� ��Ȳ�̸�
							System.out.println("ȸ�� ���� ���");
							continue;//while������ ���ư���.
						}
						else{
							 if(msgFromClient.equals(authCode)){
								//ȸ������ ����
									DBHelper.insertUser(data.getId(), data.getPasswd());
									serverMsg(Constants.REGISTER_SUCCESS);
							 }
							 else{
								 //ȸ������ ����
								 serverMsg(Constants.REGISTER_FAIL_AUTHCODE);
							 }
							
						}

					}
				
					
				}
				else if(dataType == Constants.TYPE_LOGIN) {
					boolean found = DBHelper.getUserCheck(data.getId(), data.getPasswd());
					System.out.println(id+" �α��� �õ�");
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
			//�α��� ������ ���̹Ƿ�  ���� CurrentUser�� ���� User�߰��ϰ� ���ŵ� ����Ʈ �ѷ��ֱ�
		
			synchronized (this) {
				MultiThreadChatServerSync.currentUser.add(user);
				ArrayList<User> nowCurrentUser= new ArrayList<>();
				nowCurrentUser.addAll(MultiThreadChatServerSync.currentUser);
				Data data= new Data(Constants.TYPE_USER_LIST, "", nowCurrentUser);
				for(int i=0;i<maxClientsCount;i++){
					if (threads[i] != null && threads[i].loginSuccess) { //�α��� ������ ������׸� ������
						threads[i].os.writeObject(data);
						threads[i].os.flush();
					}
				}
			}
			
			//�α���  ������ ����� ���� ȯ���޼���
			/* Welcome the new the client. */
			serverMsg("Welcome " + user.getId() + "("+user.getNickname()+") to our chat room.");
			
			synchronized (this) {
				for (int i = 0; i < maxClientsCount; i++) {
					if (threads[i] != null && threads[i] != this && threads[i].loginSuccess) { //�α��� ������ ������׸� ������
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
					//Ŭ���̾�Ʈ�� ���� ������ Ŭ��κ��� data�޾����� ó�� ��ƾ
					Data receiveData = (Data)is.readObject();
					int type = receiveData.getType();
					receiveData.setSendor(clientName);
					//������ ����� ���� �������� ���� 
					
					//�Ϲ� �޽��� Ÿ���̳�, �������� ���� ��û�� ��� ��ο��� ������.
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
						System.out.println(user.getId()+" QUIT ��û����");
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
		      
		      //���� ���� : ���� �����带 null�� ����� �ش�.
		        synchronized (this) {
		          for (int i = 0; i < maxClientsCount; i++) {
		            if (threads[i] == this) {
		              threads[i] = null;
		            }
		          }
		          
		      //������� 2: currentUser ���������� �� �ڽ��� ����, �ݿ��� currentUser�� ��ο��� �ѷ��ش�.  
		          for(int i=0;i<MultiThreadChatServerSync.currentUser.size();i++)
		          {
		        	  if(MultiThreadChatServerSync.currentUser.get(i).getId().equals(user.getId())){
		       
		        		  MultiThreadChatServerSync.currentUser.remove(i); 	
		    			  ArrayList<User> nowCurrentUser= new ArrayList<>();
		    			  nowCurrentUser.addAll(MultiThreadChatServerSync.currentUser);
		    			  Data data= new Data(Constants.TYPE_USER_LIST, "", nowCurrentUser);
		    			  for(int j=0;j<maxClientsCount;j++){
		    				  if (threads[j] != null && threads[j].loginSuccess) { //�α��� ������ ������׸� ������
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
