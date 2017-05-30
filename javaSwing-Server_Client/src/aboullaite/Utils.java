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
	
	//length �ڸ� �����ڵ带 ������ �Լ�.
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
      p.put("mail.smtp.starttls.enable", "true");     // gmail�� ������ true ����
      p.put("mail.smtp.host", "smtp.gmail.com");      // smtp ���� �ּ�
      p.put("mail.smtp.auth","true");                 // gmail�� ������ true ����
      p.put("mail.smtp.port", "587");                 // gmail ��Ʈ
         
      Authenticator auth = new MyAuthentication();
       
      //session ���� ��  MimeMessage����
      Session session = Session.getDefaultInstance(p, auth);
      MimeMessage msg = new MimeMessage(session);
       
      try{
    	  //���������ð�
          msg.setSentDate(new Date());
           
          InternetAddress from = new InternetAddress() ;
           
           
          from = new InternetAddress("UniChat<user@gmail.com>");
           
          // �̸��� �߽���
          msg.setFrom(from);
           
          // �̸��� ������
          InternetAddress to = new InternetAddress(emailAdress);
          msg.setRecipient(Message.RecipientType.TO, to);
           
          // �̸��� ����
          msg.setSubject("Unichat - �����ڵ带 Ȯ�����ּ���", "UTF-8");
           
          // �̸��� ����
          msg.setText("���� �����ڵ�� <b><u>"+Code+"</u></b> �Դϴ�.", "UTF-8");
           
          // �̸��� ���
          msg.setHeader("content-Type", "text/html");
           
          //���Ϻ�����
          javax.mail.Transport.send(msg);
      }catch (AddressException addr_e) {
          addr_e.printStackTrace();
      }catch (MessagingException msg_e) {
          msg_e.printStackTrace();
      }
  }



}


