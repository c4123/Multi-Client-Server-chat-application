package aboullaite;


import java.util.Random;
import java.util.TimerTask;

public class DonggukQuizTimer extends TimerTask {
	 public String nowAnswer ="";
	 private String QuizAnswer[][];
	 private DonggukBot mBot;
	 private boolean quizStart;
	 public String lastAnswerEmail;
	 public DonggukQuizTimer(DonggukBot bot) {
		 mBot = bot;
		 quizStart = false;
		 lastAnswerEmail="";
		// TODO Auto-generated constructor stub
		 QuizAnswer = new String[10][2];
		 for(int i=0;i<10;i++){
			 QuizAnswer[i][0] = "퀴즈 문제 "+ i+"번입니다.";
			 QuizAnswer[i][1] = i+"";
			 //정답은 i
		 }
	 
	 }
	 
	 public boolean getQuizStart(){
		 return quizStart;
	 }
	 public void setQuizStart(boolean quizStart){
		 this.quizStart = quizStart;
	 }
	 public String getLastAnswerEmail(){
		 return lastAnswerEmail;
	 }
	 public void setLastAnswerEmail(String lastAnswerEmail ){
		 this.lastAnswerEmail = lastAnswerEmail;
	 }
	@Override
	public void run() {
	// TODO Auto-generated method stub
		quizStart = false;
		Random random = new Random();
		int randNum = random.nextInt(10);
		String quiz = QuizAnswer[randNum][0];
		String answer = QuizAnswer[randNum][1];
		nowAnswer = answer;
		quizStart = true;
		mBot.sendMsg(quiz);
	}
}
