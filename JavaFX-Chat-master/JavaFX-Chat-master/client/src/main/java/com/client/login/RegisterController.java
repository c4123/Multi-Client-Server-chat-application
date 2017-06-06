package com.client.login;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;


//import org.controlsfx.tools.Platform;

import com.messages.Constants;

import javafx.application.Platform;
import aboullaite.LoginData;
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
				
				String id = mail.getText();
				String passwd = pwd.getText();
				LoginData loginData = new LoginData(id, passwd, Constants.TYPE_REGISTER);

				RegisterListener.sendRegister(loginData); //로그인 데이터 전송
				


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

	public void ShowErrorDuplicatedId() {
		Regwarning.setText("중복된 아이디 입니다.");
	}

	public void ShowConfirmationPOP() {   	
		//이 부분도 에러
		Platform.runLater(() -> {
			try {
				FXMLLoader fmxlLoader = new FXMLLoader(getClass().getResource("/views/ConfirmationPOP.fxml"));
				Parent root = fmxlLoader.load();
				POPController controller = fmxlLoader.<POPController>getController();
				
				RegisterListener.setPopController(controller);
				
				Stage stage = new Stage();
				stage.setScene(new Scene(root, 300, 120));
				stage.show();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}