package bumbums.client_android;

/**
 * Created by han sb on 2017-05-03.
 */

import android.util.Log;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import aboullaite.Data;
import aboullaite.LoginData;
import aboullaite.User;
import aboullaite.util.Constants;

public class Client {
    private Data serverMessage;
    public static String SERVERIP ; // your computer IP

    // address
    public static final int SERVERPORT = 2222;
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;

    ObjectOutputStream os;
    ObjectInputStream is;
    private ArrayList<User> currentUser;

    /**
     * Constructor of the class. OnMessagedReceived listens for the messages
     * received from server
     */
    public Client(OnMessageReceived listener){mMessageListener = listener;}
    public void setListener(OnMessageReceived listener){mMessageListener = listener;}

    /**
     * Sends the message entered by client to the server
     *
     * @param message
     *            text entered by client
     */
    public void sendMessage(Object message) {
        try {
            if(message instanceof LoginData){
                Log.d("#####","LoginDataInstance = "+((LoginData) message).getId());
                os.writeObject(message);
                os.flush();
            }
            else if(message instanceof Data){
                Log.d("#####","dataInstance = "+((Data) message).getMsg());
                os.writeObject(message);
                os.flush();
            }
        }catch (Exception ex){

        }
    }
    public void stopClient() {
        mRun = false;
    }

    public void run() {

        mRun = true;
        try { //여기선 받는것만 하는구나.
            // here you must put your computer's IP address.
            InetAddress serverAddr = InetAddress.getByName(SERVERIP);
            Log.e("#####", serverAddr.toString());
            Log.e("#####", "C: Connecting...");

            // create a socket to make the connection with the server
            Socket socket = new Socket(serverAddr, SERVERPORT);
            Log.e("#####", SERVERIP);
            try {

                // send the message to the server
                os = new ObjectOutputStream(socket.getOutputStream());
                is = new ObjectInputStream(socket.getInputStream());
                Log.e("#####", "C: Sent.");
                Log.e("#####", "C: Done.");
               /* os.writeObject(new LoginData("niutn@naver.com","1234",Constants.TYPE_LOGIN));
                os.writeObject(new Data(Constants.TYPE_MSG,"굿",null));
                os.flush();*/
                // receive the message which the server sends back

                // in this while the client listens for the messages sent by the
                // server
                while (mRun) {
                    serverMessage = (Data)is.readObject();
                        if (serverMessage != null && mMessageListener != null) {
                            // call the method messageReceived from MyActivity class
                            mMessageListener.messageReceived(serverMessage);
                            Log.e("#####", "S: Received Message: '"
                                    + serverMessage + "'");
                    }
                    serverMessage = null;
                }

            } catch (Exception e) {
                Log.e("#####", "S: Error", e);
            } finally {
                // the socket must be closed. It is not possible to reconnect to
                // this socket
                // after it is closed, which means a new socket instance has to
                // be created.
                socket.close();
            }

        } catch (Exception e) {
            Log.e("#####", "C: Error", e);
        }

    }

    // Declare the interface. The method messageReceived(String message) will
    // must be implemented in the MyActivity
    // class at on asynckTask doInBackground
    public interface OnMessageReceived {
        public void messageReceived(Data data);
    }
}