package com.client.login;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import javax.swing.Popup;

import org.controlsfx.control.PopOver;

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
		Stage stage = (Stage) Regcl.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/views/LoginView.fxml"));
		Scene scene = new Scene(root,350,420);
		stage.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.show();
		stage.setTitle("Main");
	}
	public void Regconfirm(ActionEvent event) throws Exception{
		if(Check1.isSelected() && Check2.isSelected()){
			if(pwd.getText().equals(pwdconf.getText())){
				Regwarning.setText("");
		        try {
		            Parent root = FXMLLoader.load(getClass().getResource("/views/ConfirmationPOP.fxml"));
		            Stage stage2 = new Stage();
		            stage2.setScene(new Scene(root, 300, 120));
		            stage2.show();
		        }
		        catch (IOException e) {
		            e.printStackTrace();
		        }
						
				/*Stage stage = (Stage) Regconfirm.getScene().getWindow();
				Parent root = FXMLLoader.load(getClass().getResource("/views/LoginView.fxml"));
				Scene scene = new Scene(root,350,420);
				stage.setScene(scene);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				stage.show();
				stage.setTitle("Main");*/
			}
			else
				Regwarning.setText("비밀번호가 일치하지 않습니다");
		}
		else{
			Regwarning.setText("약관에 대해 동의하지 않았습니다");
		}
	}

}