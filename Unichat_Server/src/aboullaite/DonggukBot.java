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
		donggukQuizTimer = new DonggukQuizTimer(this);
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
							System.out.println("[[[동국봇]]] <"+data.getSendorEmail()+">"+data.getMsg());   
							if(donggukQuizTimer.getQuizStart()){ //퀴즈 진행중이면
								if(data.getMsg().equals(donggukQuizTimer.nowAnswer)){
									//정답일 경우게만 
									donggukQuizTimer.setQuizStart(false);
									donggukQuizTimer.setLastAnswerEmail(data.getSendorEmail());
									sendMsg(data.getSendorNickName()+"님이 정답을 맞추셨습니다! "+data.getSendorNickName()+"님이 이후로 처음하시는 채팅은 공지사항으로 등록됩니다!");
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
							
							/*
							//메뉴 출력
							String[] menu = data.getMsg().split("\\s", 2);
							if(menu[0].equals("교직원식당")) {
								Parsing parsing = new Parsing();
								Res res = parsing.getEmployeeData();
								String resName = res.getResName();
								ArrayList<Corner> corner =  res.getCorners();
								if(menu[1].equals("집밥")) {
									System.out.println("집밥");
									for(int i = 0; i < corner.get(1).getFoods().size(); i++)
										sendMsg(corner.get(1).getFoods().get(i));
								} else if(menu[1].equals("한그릇")) {
									System.out.println("한그릇");
									for(int i = 0; i < corner.get(2).getFoods().size(); i++)
										sendMsg(corner.get(2).getFoods().get(i));									
								} else if(menu[1].equals("채식당")) {
									System.out.println("채식당");
									for(int i = 0; i < corner.get(0).getFoods().size(); i++)
										sendMsg(corner.get(0).getFoods().get(i));
								}
							}
							*/
							
							if(data.getMsg().equals("교직원식당")) {
								Parsing parsing = new Parsing();
								Res res = parsing.getEmployeeData();
								String resName = res.getResName();
								ArrayList<Corner> corner =  res.getCorners();
								for(int i = 0; i < corner.size(); i++) {
									for(int j = 0; j < corner.get(i).getFoods().size(); j++) {
										sendMsg(corner.get(i).getFoods().get(j));
									}
								}
							}							
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

		startQuiz();


	}

	public void startQuiz(){
		Timer timer = new Timer();
		timer.schedule(donggukQuizTimer, 30000, 120000);
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
