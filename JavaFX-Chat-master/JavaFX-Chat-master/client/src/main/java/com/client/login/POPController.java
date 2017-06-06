package com.client.login;

import java.io.IOException;

import org.controlsfx.tools.Platform;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

public class POPController{


	@FXML private Button confbtn;
	@FXML private Label error;
	@FXML private TextField confinput;
	
	public void Conf(ActionEvent event) throws Exception{
		if(confinput.getText().equals("abc"))//인증번호 확인부 (현재는 abc 라고 해 둠)
			((Node)(event.getSource())).getScene().getWindow().hide();
		else{ 
			error.setText("인증번호가 일치하지 않습니다"); //인증번호 틀릴 경우 에러 메세지 띄우며 창은 유지
		}
	}
}