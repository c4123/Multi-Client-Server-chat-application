package aboullaite;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class DonggukBot {
	
    private Socket socket;
    //private OutputStream outputStream;
    private ObjectOutputStream os;
    private ObjectInputStream is;
    private ArrayList<User> currentUser;
    
	 /** Create socket, and receiving thread */
    public void InitSocket(String server, int port) throws IOException {
        socket = new Socket(server, port);
        os = new ObjectOutputStream(socket.getOutputStream());
        is = new ObjectInputStream(socket.getInputStream());
        
        String result="";
        String userId = "DONGGUKBOT";
		String passWd = "1q2w3e4r!";
		LoginData loginData = new LoginData(userId,passWd,Constants.TYPE_LOGIN);
		
		os.writeObject(loginData);
		os.writeObject(new Data(Constants.TYPE_MSG,"DonggukBot",null));
		os.flush();
		
		
		try {
			result = ((Data)is.readObject()).getMsg();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("login Result: "+result);
		
		if(result.equals(Constants.LOGIN_FAILED)){
			System.out.println("DonggukBOT 로그인 실패.");
			return;
		}
		
        Thread receivingThread = new Thread() {
            @Override
            public void run() {
                try {
                	Data data ;
                	 while ((data = (Data)is.readObject()) != null){
                			if(data.getType()==Constants.TYPE_MSG){     	
                				System.out.println("[[[동국봇]]] <"+data.getSendor()+">"+data.getMsg());   
                           	}
                           	else if(data.getType()==Constants.TYPE_USER_LIST){
                           		//현재 유저 갱신되면 받는 부분.
                           		currentUser = data.getUserList();
                           		System.out.println("유저 리스트 새로 받음");
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
    }

}
