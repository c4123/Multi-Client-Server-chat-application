package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import application.Resize;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import aboullaite.LoginData;
import aboullaite.Constants;
import aboullaite.Data;;
public class MainController {
	private Socket socket;
    private ObjectOutputStream os;
    private ObjectInputStream is;
	
	@FXML
	private Label lblstat; //label state ex)failed, ok
	@FXML
	private TextField Username;
	@FXML
	private TextField pwd;
	@FXML
	private TextField ip;
	@FXML
	private Button Login;
	@FXML
	private Button Register;
	
	public void InitSocket(String server, int port) throws IOException {
		try {
			socket = new Socket(server, port);
			os = new ObjectOutputStream(socket.getOutputStream());
			is = new ObjectInputStream(socket.getInputStream());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void Login(ActionEvent event) throws Exception {
		String result = "";
		Stage stage = null;
		Parent newScene = null;
		
		InitSocket(ip.getText(), 2222);
		
		LoginData loginData = new LoginData(Username.getText(), pwd.getText(), Constants.TYPE_LOGIN);
		os.writeObject(loginData);
		os.flush();
		
		try {
			result = ((Data)is.readObject()).getMsg();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		if(result.equals(Constants.LOGIN_FAILED)){
			lblstat.setText("Failed");
			return;
		}
		
		lblstat.setText("OK");
		stage = (Stage) Login.getScene().getWindow();
		newScene = FXMLLoader.load(getClass().getResource("/application/ChatView.fxml"));
		Scene scene = new Scene(newScene,500,500);
		Resize.addResizeListener(stage);
		stage.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.show();
		stage.setTitle("Chat");	
	}
	
	public void Register(ActionEvent event) throws Exception {
		Stage stage = (Stage) Register.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Register.fxml"));
		Scene scene = new Scene(root,300,500);
		stage.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.show();
		stage.setTitle("Register");
	}
}

