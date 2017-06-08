package aboullaite;


import java.util.Random;
import java.util.TimerTask;

public class DonggukQuizTimer extends TimerTask {
	 public String nowAnswer ="";
	 private String quizAnswer[][];
	 private DonggukBot mBot;
	 private boolean quizStart;
	 public String lastAnswerEmail;
	 public DonggukQuizTimer(DonggukBot bot) {
		 mBot = bot;
		 quizStart = false;
		 lastAnswerEmail="";
		// TODO Auto-generated constructor stub
		 DBHelper.getConnection();
		 quizAnswer = DBHelper.getQuiz();
		 System.out.println("ƒ˚¡Ó µ•¿Ã≈Õ "+quizAnswer.length+"∞≥ πﬁæ∆ø»");
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
		if(quizAnswer.length!=0){
			Random random = new Random();
			int randNum = random.nextInt(quizAnswer.length);
			String quiz = quizAnswer[randNum][0];
			String answer = quizAnswer[randNum][1];
			nowAnswer = answer;
			quizStart = true;
			mBot.sendMsg(quiz);
		}
	}
}
