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
		if(confinput.getText().equals("abc"))//������ȣ Ȯ�κ� (����� abc ��� �� ��)
			((Node)(event.getSource())).getScene().getWindow().hide();
		else{ 
			error.setText("������ȣ�� ��ġ���� �ʽ��ϴ�"); //������ȣ Ʋ�� ��� ���� �޼��� ���� â�� ����
		}
	}
}