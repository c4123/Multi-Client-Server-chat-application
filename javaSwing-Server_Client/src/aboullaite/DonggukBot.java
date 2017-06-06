package aboullaite;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;

import javax.swing.JOptionPane;

public class DonggukBot {
	
    private Socket socket;
    //private OutputStream outputStream;
    private ObjectOutputStream os;
    private ObjectInputStream is;
    private ArrayList<User> currentUser;
    private DonggukQuizTimer donggukQuizTimer ;
    private boolean canNotification;
	 /** Create socket, and receiving thread */
    public void InitSocket(String server, int port) throws IOException {
        socket = new Socket(server, port);
        os = new ObjectOutputStream(socket.getOutputStream());
        is = new ObjectInputStream(socket.getInputStream());
        String result="";
        canNotification = false;
        String userId = "DONGGUKBOT";
		String passWd = "1q2w3e4r!!";
		LoginData loginData = new LoginData(userId,passWd,Constants.TYPE_LOGIN);
		
		os.writeObject(loginData);
		os.writeObject(new Data(Constants.TYPE_MSG,Constants.DonggukBotName,null));
		os.flush();
		
		
		try {
			result = ((Data)is.readObject()).getMsg();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("login Result: "+result);
		
		if(result.equals(Constants.LOGIN_FAILED)){
			System.out.println("DonggukBOT �α��� ����.");
			return;
		}
		
		
        Thread receivingThread = new Thread() {
            @Override
            public void run() {
                try {
                	Data data ;
                	 while ((data = (Data)is.readObject()) != null){
                			if(data.getType()==Constants.TYPE_MSG){     	
                				System.out.println("[[[������]]] <"+data.getSendorEmail()+">"+data.getMsg());   
                				if(donggukQuizTimer.getQuizStart()){ //���� �������̸�
                					if(data.getMsg().equals(donggukQuizTimer.nowAnswer)){
                						//������ ���Ը� 
                						donggukQuizTimer.setQuizStart(false);
                						donggukQuizTimer.setLastAnswerEmail(data.getSendorEmail());
                						sendMsg(data.getSendorNickName()+"���� ������ ���߼̽��ϴ�! "+data.getSendorNickName()+"���� ���ķ� ó���Ͻô� ä���� ������������ ��ϵ˴ϴ�!");
                						canNotification = true;
                					}
                				}
                				else{
                					if(canNotification){
                						if(data.getSendorEmail().equals(donggukQuizTimer.getLastAnswerEmail())){
                							canNotification = false;
                							Data notiData = new Data(Constants.TYPE_NOTIFICATION,data.getMsg(),null);
                							os.writeObject(notiData);
                							os.flush();
                						}
                					}
                				}
                				
                           	}
                           	else if(data.getType()==Constants.TYPE_USER_LIST){
                           		//���� ���� ���ŵǸ� �޴� �κ�.
                           		currentUser = data.getUserList();
                           		System.out.println("���� ����Ʈ ���� ����");
                           		/* for(int i=0;i<currentUser.size();i++){
                           			System.out.println(currentUser.get(i).getId());
                           		}*/
                           	}
                     }
      
                } catch (IOException ex ) {
                	System.out.println(ex);
                } catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        };
        receivingThread.start();
        
        startQuiz();
        
        
    }
    
    public void startQuiz(){
		donggukQuizTimer = new DonggukQuizTimer(this);
		Timer timer = new Timer();
		timer.schedule(donggukQuizTimer, 10000, 60000);
    }
    public void sendMsg(String quiz){
		Data quizData = new Data(Constants.TYPE_MSG,quiz,null);
		quizData.setSendorEmail(Constants.DonggukBotEmail);
		quizData.setSendorNickName(Constants.DonggukBotName);
		
		try {
			os.writeObject(quizData);
			os.flush();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
    }
    public void sendNotification(){
    	
    }

}
