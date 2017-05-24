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
import aboullaite.Constants;;
public class MainController {
	private Socket socket;
    //private OutputStream outputStream;
    private ObjectOutputStream os;
    private ObjectInputStream is;
	
	@FXML
	private Label lblstat;
	@FXML
	private TextField Username;
	@FXML
	private TextField pwd;
	@FXML
	private Button Login;
	@FXML
	private Button Register;
	
	public void InitSocket(String server, int port) throws IOException {
		try{
			// TODO Auto-generated constructor stub
			socket = new Socket(server, 2222);
			//outputStream = socket.getOutputStream();
			os = new ObjectOutputStream(socket.getOutputStream());
			is = new ObjectInputStream(socket.getInputStream());
		}catch(Exception e){}
	}
	
	
	public void Login(ActionEvent event) throws Exception{
		Stage stage = null;
		Parent newScene = null;
		/*String id = Username.getText();
		String pw = pwd.getText();
		
		LoginData loginData = new LoginData(id, pw, Constants.TYPE_LOGIN);
		os.writeObject(loginData);
		os.flush();
		String result = is.readUTF();
		System.out.println(result);*/
		
		if(Username.getText().equals("abc")){
			lblstat.setText("OK");
			stage = (Stage) Login.getScene().getWindow();
			newScene = FXMLLoader.load(getClass().getResource("/application/Chat.fxml"));
			Scene scene = new Scene(newScene,500,500);
			Resize.addResizeListener(stage);
			stage.setScene(scene);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.show();
			stage.setTitle("Chat");		
		}
		else{
			lblstat.setText("Failed");
		
		}
	}
	public void Register(ActionEvent event) throws Exception{
		Stage stage = (Stage) Register.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Register.fxml"));
		Scene scene = new Scene(root,300,500);
		stage.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.show();
		stage.setTitle("Register");
	}
}

