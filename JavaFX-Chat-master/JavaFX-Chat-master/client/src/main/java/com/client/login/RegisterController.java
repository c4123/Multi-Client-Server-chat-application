package com.client.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RegisterController{


	@FXML private Button Regcl;
	@FXML private Button Regconfirm;
	@FXML private Button Mailconfirm;
	@FXML private CheckBox Check1;
	@FXML private CheckBox Check2;
	@FXML private Label Regwarning;
	@FXML private TextField mail;
	@FXML private TextField pwd;
	@FXML private TextField pwdconf;

	public void Regcl(ActionEvent event) throws Exception{
		/*
	}

		Stage stage = (Stage) Regcl.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/application/LoginView.fxml"));
		Scene scene = new Scene(root,300,500);
		stage.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.show();
		stage.setTitle("Main");
		*/
	}
	
	public void Regconfirm(ActionEvent event) throws Exception{
		if(Check1.isSelected() && Check2.isSelected()){
			Stage stage = (Stage) Regconfirm.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("/application/LoginView.fxml"));
			Scene scene = new Scene(root,300,500);
			stage.setScene(scene);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.show();
			stage.setTitle("Main");
		}
		else{
			Regwarning.setText("약관에 대해 동의하지 않았습니다");
		}
	}


	public void Mailconfirm(ActionEvent event) throws Exception{
		/*
		try {
			socket = new Socket(port, 2222);
			outputStream = socket.getOutputStream();
			oos = new ObjectOutputStream(outputStream);
			is = socket.getInputStream();
			input = new ObjectInputStream(is);	
		} catch (IOException e) {
			;
		}

		LoginData loginData = new LoginData(mail.getText(), pwd.getText(), Constants.T);

		try {
			oos.writeObject(loginData);
			oos.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(pwd.getText().equals(pwdconf.getText())){
			Regwarning.setText("");
		}
		else{
			Regwarning.setText("비밀번호가 일치하지 않습니다");
		}
	}
	*/

	}
}