package aboullaite;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MyAuthentication extends Authenticator {
    
	  PasswordAuthentication pa;
	  public MyAuthentication(){

	      String id = "unichatserver@gmail.com";       // ���� ID
	      String pw = "1q2w3e4r!";          // ���� ��й�ȣ
	      // ID�� ��й�ȣ�� �Է��Ѵ�.
	      pa = new PasswordAuthentication(id, pw);   
	  }
	  // �ý��ۿ��� ����ϴ� ��������
	  public PasswordAuthentication getPasswordAuthentication() {
	      return pa;
	  }
}