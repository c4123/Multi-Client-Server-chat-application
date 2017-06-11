package aboullaite;

import javax.swing.*;

//Class to precise who is connected : Client or Server
public class Server {	
	public static void main(String [] args){
            String[] arguments = new String[] {};
			new MultiThreadChatServerSync();
			MultiThreadChatServerSync.main(arguments);
	}
}