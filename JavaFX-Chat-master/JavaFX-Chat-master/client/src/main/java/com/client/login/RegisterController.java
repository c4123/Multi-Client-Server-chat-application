package com.client.login;

import java.io.IOException;

import org.controlsfx.tools.Platform;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
		Platform.runLater(() -> {
			FXMLLoader fmxlLoader = new FXMLLoader(getClass().getResource("/views/LoginView.fxml"));
			Parent window = null;
			try {
				window = (Pane) fmxlLoader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Stage stage = MainLauncher.getPrimaryStage();
			Scene scene = new Scene(window);
			stage.setMaxWidth(350);
			stage.setMaxHeight(420);
			stage.setResizable(false);
			stage.setScene(scene);
			stage.centerOnScreen();
		});
	}
	public void Regconfirm(ActionEvent event) throws Exception{
		if(Check1.isSelected() && Check2.isSelected()){
	        Platform.runLater(() -> {
	            FXMLLoader fmxlLoader = new FXMLLoader(getClass().getResource("/views/LoginView.fxml"));
	            Parent window = null;
	            try {
	                window = (Pane) fmxlLoader.load();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            Stage stage = MainLauncher.getPrimaryStage();
	            Scene scene = new Scene(window);
	            stage.setMaxWidth(350);
	            stage.setMaxHeight(420);
	            stage.setResizable(false);
	            stage.setScene(scene);
	            stage.centerOnScreen();
	        });
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