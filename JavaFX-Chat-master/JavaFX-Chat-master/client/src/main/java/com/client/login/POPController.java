package com.client.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class POPController{


	@FXML private Button confbtn;
	@FXML private Label error;
	@FXML private TextField confinput;

	public void Conf(ActionEvent event) throws Exception{
		RegisterListener.sendAuthcode(confinput.getText());
	}

	public void ShowErrorInvaildCode() {
		//�̰� UI ������Ʈ �� �� �ְ���..
		error.setText("������ȣ�� ��ġ���� �ʽ��ϴ�"); //������ȣ Ʋ�� ��� ���� �޼��� ���� â�� ����
	}

	public void closePOPWindow() {
		//�� �κ� ����
		((Node)(event.getSource())).getScene().getWindow().hide();
	}
}
