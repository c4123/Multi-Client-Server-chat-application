package com.client.login;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class POPController{


	@FXML private Button confbtn;
	@FXML private Label error;
	@FXML private TextField confinput;

	public void Conf(ActionEvent event) throws Exception{
		RegisterListener.sendAuthcode(confinput.getText());
	}

	public void ShowErrorInvaildCode() {
		//이거 UI 업데이트 할 수 있게좀..
		error.setText("인증번호가 일치하지 않습니다"); //인증번호 틀릴 경우 에러 메세지 띄우며 창은 유지
	}

	public void closePOPWindow() {
		//이 부분 에러
		Platform.runLater(() -> {
			Stage stage = (Stage) confbtn.getScene().getWindow();
			stage.close();

			//((Node)(event.getSource())).getScene().getWindow().hide();
		});
	}
}
