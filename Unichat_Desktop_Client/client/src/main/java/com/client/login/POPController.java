package com.client.login;

import java.io.IOException;

import com.client.chatwindow.Listener;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class POPController{


	@FXML private Button confbtn;
	@FXML private Label error;
	@FXML private TextField confinput;
	
	@FXML
    public void closePopup() throws IOException {
			//Listener.closeAuth();
			Stage stage = new Stage();
			stage.close();
    }

	public void Conf(ActionEvent event) throws Exception{
		RegisterListener.sendAuthcode(confinput.getText());
	}

	public void ShowErrorInvaildCode() {
		//이거 UI 업데이트 할 수 있게좀..
		error.setText("인증번호가 일치하지 않습니다"); //인증번호 틀릴 경우 에러 메세지 띄우며 창은 유지
	}

	public void Popconf() {
		//이 부분 에러
		Platform.runLater(() -> {
			Stage stage = (Stage) confbtn.getScene().getWindow();
			stage.close();

			//((Node)(event.getSource())).getScene().getWindow().hide();
		});
		
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
}
