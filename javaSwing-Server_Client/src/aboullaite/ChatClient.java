package aboullaite;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


// Class to manage Client chat Box.
public class ChatClient {

    /** Chat client access */
    static class ChatAccess extends Observable {
        private Socket socket;
        //private OutputStream outputStream;
        private ObjectOutputStream os;
        private ObjectInputStream is;
        private ArrayList<User> currentUser;
        @Override
        public void notifyObservers(Object arg) {
            super.setChanged();
            super.notifyObservers(arg);
        }

        /** Create socket, and receiving thread */
        public void InitSocket(String server, int port) throws IOException {
            socket = new Socket(server, port);
            //outputStream = socket.getOutputStream();
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());
            
            
            //LOGIN 창
            String result="";
            String userId = JOptionPane.showInputDialog("Email Address(test@dongguk.edu로는 로그인 안됨)");
			String passWd = JOptionPane.showInputDialog("Password");
			LoginData loginData = new LoginData(userId,passWd,Constants.TYPE_LOGIN);
			
			os.writeObject(loginData);
			os.flush();
			
			
			try {
				result = ((Data)is.readObject()).getMsg();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("login Result: "+result);
			
			if(result.equals(Constants.LOGIN_FAILED)){
				notifyObservers("로그인에 실패하셨습니다.");
				return;
			}
			
            Thread receivingThread = new Thread() {
                @Override
                public void run() {
                    try {
                    	//is = new ObjectInputStream(socket.getInputStream());
                    	Data data ;
                    	 while ((data = (Data)is.readObject()) != null){
                    			if(data.getType()==Constants.TYPE_MSG){     	
                               		notifyObservers("<"+data.getSendor()+">"+data.getMsg());   
                               	}
                               	else if(data.getType()==Constants.TYPE_USER_LIST){
                               		//현재 유저 갱신되면 받는 부분.
                               		currentUser = data.getUserList();
                               		System.out.println("유저 리스트 새로 받음");
                               		for(int i=0;i<currentUser.size();i++){
                               			System.out.println(currentUser.get(i).getId());
                               		}
                               	}
                         }
                        	
                       
                       
                    } catch (IOException ex ) {
                        notifyObservers(ex);
                    } catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						  notifyObservers(e);
					}
                }
            };
            receivingThread.start();
        }

        private static final String CRLF = "\r\n"; // newline

        /** Send a line of text */
        public void send(String text) {
            try {           	            	
            	os.writeObject(new Data(Constants.TYPE_MSG,text,null));
                os.flush();
            } catch (Exception ex) {
                notifyObservers(ex);
            }
        }

        /** Close the socket */
        public void close() {
            try {
                socket.close();
            } catch (IOException ex) {
                notifyObservers(ex);
            }
        }
    }

    /** Chat client UI */
    static class ChatFrame extends JFrame implements Observer {
    	private JTabbedPane tabbedPane;
    	private JPanel userListPanel;
        private JTextArea textArea;
        private JTextField inputTextField;
        private JButton sendButton;
        private ChatAccess chatAccess;

        public ChatFrame(ChatAccess chatAccess) {
        	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	
            this.chatAccess = chatAccess;
            chatAccess.addObserver(this);
            buildGUI();
        }

        /** Builds the user interface */
        private void buildGUI() {
            textArea = new JTextArea(20, 50);
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            add(new JScrollPane(textArea), BorderLayout.CENTER);
            
            tabbedPane = new JTabbedPane();
            userListPanel = new JPanel();
            tabbedPane.add("User List", userListPanel);
            add(tabbedPane, BorderLayout.EAST);

            Box box = Box.createHorizontalBox();
            add(box, BorderLayout.SOUTH);
            inputTextField = new JTextField();
            sendButton = new JButton("Send");
            box.add(inputTextField);
            box.add(sendButton);

            // Action for the inputTextField and the goButton
            ActionListener sendListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String str = inputTextField.getText();
                    if (str != null && str.trim().length() > 0)
                        chatAccess.send(str);
                    inputTextField.selectAll();
                    inputTextField.requestFocus();
                    inputTextField.setText("");
                }
            };
            inputTextField.addActionListener(sendListener);
            sendButton.addActionListener(sendListener);

            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    chatAccess.close();
                }
            });
        }

        /** Updates the UI depending on the Object argument */
		public void update(Observable o, Object arg) {
            final Object finalArg = arg;
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    textArea.append(finalArg.toString());
                    textArea.append("\n");
                }
            });
        }
    }

    public static void main(String[] args) {
        String server = args[0];
        int port =2222;
        ChatAccess access = new ChatAccess();

        JFrame frame = new ChatFrame(access);
        frame.setTitle("MyChatApp - connected to " + server + ":" + port);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        try {
            access.InitSocket(server,port);
            
        } catch (IOException ex) {
            System.out.println("Cannot connect to " + server + ":" + port);
            ex.printStackTrace();
            System.exit(0);
        }
    }
}