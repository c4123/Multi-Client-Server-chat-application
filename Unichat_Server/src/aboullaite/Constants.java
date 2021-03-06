package aboullaite;

public class Constants {
	//constants for login data
	public static final String SERVER_CONNECTED = "serverConnected";
	public static final int TYPE_REGISTER=0;
	public static final int TYPE_LOGIN =1;
	public static final String LOGIN_SUCCESS = "loginSuccess";
	public static final String LOGIN_FAILED = "loginFail";
	public static final String REGISTER_SUCCESS = "registerSuccess";
	public static final String REGISTER_FAIL_ID= "registerFailIdExist";
	public static final String REGISTER_WAITING_AUTHCODE="waitingAuthCode";
	public static final String REGISTER_FAIL_AUTHCODE= "registerFailIdAuthCode";
	public static final String REGISTER_CANCEL = "registerCancel";
	public static final String REGISTER_TIME_EXCEEDED = "registerTimeExceeded";


	//for just data
	public static final int TYPE_MSG = 0;
	public static final int TYPE_WHISPER = 1;
	public static final int TYPE_USER_LIST = 2;
	public static final int TYPE_QUIT = 3;
	public static final int TYPE_NOTIFICATION = 4;
	
	//for just DONGGUK bot
	public static final String DonggukBotName = "DonggukBot";
	public static final String DonggukBotEmail = "DONGGUKBOT";
	
}
