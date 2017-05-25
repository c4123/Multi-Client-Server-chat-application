package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RegisterController{
	
	
	@FXML private Button Regcl;
	@FXML private Button Regconfirm;
	@FXML private Button Mailconfirm;
	@FXML private CheckBox Check1;
	@FXML private CheckBox Check2;
	@FXML private Label Regwarning;
	@FXML private PasswordField pwd;
	@FXML private PasswordField pwdconf;
	
	public void Regcl(ActionEvent event) throws Exception{
		
		Stage stage = (Stage) Regcl.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Login.fxml"));
		Scene scene = new Scene(root,300,500);
		stage.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.show();
		stage.setTitle("Main");
	}
	public void Regconfirm(ActionEvent event) throws Exception{
		
		if(Check1.isSelected() && Check2.isSelected()){
			Stage stage = (Stage) Regconfirm.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("/application/Login.fxml"));
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
		
		
		if(pwd.getText().equals(pwdconf.getText())){
			Regwarning.setText("");
		}
		else
			Regwarning.setText("비밀번호가 일치하지 않습니다");
	}
	
}
