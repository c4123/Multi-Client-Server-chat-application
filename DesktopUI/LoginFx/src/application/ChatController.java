package application;

import java.io.IOException;
import application.Resize;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatController {
	@FXML private Button msgsend;
	@FXML private ScrollPane userlist;
	@FXML private TextField messagebox;
	@FXML private ListView chatscreen;
	/* 
	 
	 public void sendButtonAction() throws IOException {
	        String msg = messageBox.getText();
	        if (!messageBox.getText().isEmpty()) {
	        	Listener.send(msg);
	            messageBox.clear();
	        }
	    }*/
}
