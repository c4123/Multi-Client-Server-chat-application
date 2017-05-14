package aboullaite;

import javax.swing.JOptionPane;

public class Client {
	public static void main(String[] args) {
			String IPServer = JOptionPane.showInputDialog("Enter the Server ip adress");
			String[] arguments = new String[] {IPServer};
			new ChatClient();
			ChatClient.main(arguments);
		
	}
}
