package aboullaite;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class Utils {
	
	//length 자리 인증코드를 만들어내는 함수.
	public static String generateNumber(int length) {
		 
	    String numStr = "1";
	    String plusNumStr = "1";
	 
	    for (int i = 0; i < length; i++) {
	        numStr += "0";
	 
	        if (i != length - 1) {
	            plusNumStr += "0";
	        }
	    }
	 
	    Random random = new Random();
	    int result = random.nextInt(Integer.parseInt(numStr)) + Integer.parseInt(plusNumStr);
	 
	    if (result > Integer.parseInt(numStr)) {
	        result = result - Integer.parseInt(plusNumStr);
	    }
	 
	    return Integer.toString(result);
	}
	
	public static void sendMail(String emailAdress, String Code) {
		// TODO Auto-generated method stub
		
	  Properties p = System.getProperties();
      p.put("mail.smtp.starttls.enable", "true");     // gmail은 무조건 true 고정
      p.put("mail.smtp.host", "smtp.gmail.com");      // smtp 서버 주소
      p.put("mail.smtp.auth","true");                 // gmail은 무조건 true 고정
      p.put("mail.smtp.port", "587");                 // gmail 포트
         
      Authenticator auth = new MyAuthentication();
       
      //session 생성 및  MimeMessage생성
      Session session = Session.getDefaultInstance(p, auth);
      MimeMessage msg = new MimeMessage(session);
       
      try{
    	  //편지보낸시간
          msg.setSentDate(new Date());
           
          InternetAddress from = new InternetAddress() ;
           
           
          from = new InternetAddress("UniChat<user@gmail.com>");
           
          // 이메일 발신자
          msg.setFrom(from);
           
          // 이메일 수신자
          InternetAddress to = new InternetAddress(emailAdress);
          msg.setRecipient(Message.RecipientType.TO, to);
           
          // 이메일 제목
          msg.setSubject("Unichat - 인증코드를 확인해주세요", "UTF-8");
           
          // 이메일 내용
          msg.setText("가입 인증코드는 <b><u>"+Code+"</u></b> 입니다.", "UTF-8");
           
          // 이메일 헤더
          msg.setHeader("content-Type", "text/html");
           
          //메일보내기
          javax.mail.Transport.send(msg);
      }catch (AddressException addr_e) {
          addr_e.printStackTrace();
      }catch (MessagingException msg_e) {
          msg_e.printStackTrace();
      }
  }



}


