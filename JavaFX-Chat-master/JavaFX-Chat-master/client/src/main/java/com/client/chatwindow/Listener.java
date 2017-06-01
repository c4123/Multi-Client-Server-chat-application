package com.client.chatwindow;

import static com.messages.MessageType.CONNECTED;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.ResultSetMetaData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.client.login.LoginController;
import com.messages.Constants;
import com.messages.Message;
import com.messages.MessageType;
import com.messages.Status;

import aboullaite.Data;
import aboullaite.LoginData;

public class Listener implements Runnable{

    private static final String HASCONNECTED = "has connected";

    private static String picture;
    private Socket socket;
    public String hostname;
    public String port;
    public static String username;
    public ChatController controller;
    private static ObjectOutputStream oos;
    private InputStream is;
    private ObjectInputStream input;
    private OutputStream outputStream;
    Logger logger = LoggerFactory.getLogger(Listener.class);
    
    
    //호스트네임이 비번, 유저네임이 아이디, 포트가 아이피
    public Listener(String hostname,  String port, String username, String picture, ChatController controller) {
        this.hostname = hostname;
        this.port = port;
        Listener.username = username;
        Listener.picture = picture;
        this.controller = controller;
    }

    public void run() {
        try {
            //socket = new Socket(hostname, port);
        	socket = new Socket(port, 2222);
            LoginController.getInstance().showScene();
            outputStream = socket.getOutputStream();
            oos = new ObjectOutputStream(outputStream);
            is = socket.getInputStream();
            input = new ObjectInputStream(is);
        } catch (IOException e) {
            LoginController.getInstance().showErrorDialog("Could not connect to server");
            logger.error("Could not Connect");
        }
        logger.info("Connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
        
        
        try {
			Data msg = (Data)input.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        /*
         *로그인 
         */
		LoginData loginData = new LoginData(username, hostname, Constants.TYPE_LOGIN);
		
		try {
			oos.writeObject(loginData);
			oos.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*
		 * 대화 시작
		 */
        try {
            //connect();
            logger.info("Sockets in and out ready!");
            while (socket.isConnected()) {
                Data message = null;
                message = (Data) input.readObject();
                if (message != null) {
                    logger.debug("Message recieved:" + message.getMsg() + " MessageType:" + message.getType() + "Name:" + message.getSendor());
                    switch (message.getType()) {
                        case Constants.TYPE_MSG:
                            controller.addToChat(message);
                            break;
                            /*
                        case VOICE:
                            controller.addToChat(message);
                            break;
                        case NOTIFICATION:
                            controller.newUserNotification(message);
                            break;
                        case SERVER:
                            controller.addAsServer(message);
                            break;
                            */
                        case Constants.TYPE_USER_LIST:
                            controller.setUserList(message);
                            break;
                            /*
                        case DISCONNECTED:
                            controller.setUserList(message);
                            break;
                        case STATUS:
                            controller.setUserList(message);
                            break;
                            */
                          default:
                        	  break;
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            controller.logoutScene();
        }
    }

    /* This method is used for sending a normal Message
     * @param msg - The message which the user generates
     */
    public static void send(String msg) throws IOException {
        Data createMessage = new Data(Constants.TYPE_MSG, msg, null);
        oos.writeObject(createMessage);
        oos.flush();
    }

    /* This method is used for sending a voice Message
 * @param msg - The message which the user generates
 */
    public static void sendVoiceMessage(byte[] audio) throws IOException {
        Message createMessage = new Message();
        createMessage.setName(username);
        createMessage.setType(MessageType.VOICE);
        createMessage.setStatus(Status.AWAY);
        createMessage.setVoiceMsg(audio);
        createMessage.setPicture(picture);
        oos.writeObject(createMessage);
        oos.flush();
    }

    /* This method is used for sending a normal Message
 * @param msg - The message which the user generates
 */
    public static void sendStatusUpdate(Status status) throws IOException {
        Message createMessage = new Message();
        createMessage.setName(username);
        createMessage.setType(MessageType.STATUS);
        createMessage.setStatus(status);
        createMessage.setPicture(picture);
        oos.writeObject(createMessage);
        oos.flush();
    }

    /* This method is used to send a connecting message */
    public static void connect() throws IOException {
        Message createMessage = new Message();
        createMessage.setName(username);
        createMessage.setType(CONNECTED);
        createMessage.setMsg(HASCONNECTED);
        createMessage.setPicture(picture);
        oos.writeObject(createMessage);
    }

}
