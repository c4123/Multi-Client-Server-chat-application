package bumbums.client_android;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import aboullaite.Data;
import aboullaite.LoginData;
import aboullaite.util.Constants;

public class LoginActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Data> {

    private Client mClient;
    private EditText mEmail;
    private EditText mPasswd;
    private EditText mServerIP;
    private TextView mInfo;
    private Button mLoginBtn;
    private Button mJoinBtn;
    private Button mServerBtn;
    private ProgressBar mLoadingBar;
    private final int LOGIN_LOADER = 1;
    private boolean mIsServerConnected ;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmail = (EditText) findViewById(R.id.et_email);
        mPasswd = (EditText) findViewById(R.id.et_passwd);
        mServerIP = (EditText) findViewById(R.id.et_ipaddress);
        mInfo = (TextView)findViewById(R.id.tv_info);
        mJoinBtn = (Button) findViewById(R.id.btn_join);
        mLoginBtn = (Button) findViewById(R.id.btn_login);
        mServerBtn = (Button) findViewById(R.id.btn_serverAccess);
        mLoadingBar = (ProgressBar)findViewById(R.id.pb_loading);
        // connect to the server
        final LoaderManager loaderManager = getSupportLoaderManager();
        final Loader<Data> loginLoader = loaderManager.getLoader(LOGIN_LOADER);

        mClient = Client.getInstance();
        mIsServerConnected = false;
        //서버 접속
        mServerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClient.SERVERIP = mServerIP.getText().toString();
                if (loginLoader == null) {
                    loaderManager.initLoader(LOGIN_LOADER, null, LoginActivity.this).forceLoad();
                } else {
                    loaderManager.restartLoader(LOGIN_LOADER, null, LoginActivity.this);
                }
            }
        });

        //로그인
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIsServerConnected) {
                    final String id = mEmail.getText().toString();
                    final String passwd = mPasswd.getText().toString();

                    mLoadingBar.setVisibility(View.VISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            LoginData loginData = new LoginData(id, passwd, Constants.TYPE_LOGIN);
                            mClient.sendMessage(loginData);
                        }
                    }).start();
                }
                else{
                    mInfo.setText("서버를 먼저 연결해주세요");
                }

            }
        });


        mJoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });
        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if(s.toString()!=null) {
                    if(str.length()!=0) {
                        char last = str.charAt(str.length() - 1);
                        if (last == '@') {
                            mEmail.setText(str += "dongguk.edu");
                            mEmail.setSelection(mEmail.length());
                        }
                    }
                }
            }
        });

    }


    @Override
    public Loader<Data> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<Data>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                Log.d("#####", "onstartLoding");
                mClient.setListener(new Client.OnMessageReceived() {
                    @Override
                    public void messageReceived(Data data) {
                        deliverResult(data);
                    }
                });
            }

            @Override
            public Data loadInBackground() {
                Log.d("#####", "doinBack");
                mClient.run();
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Data> loader, Data data) {
        if (data != null) {
            Log.d("#####", "들어온데이터" + data.getMsg());
            if (data.getMsg().equals(Constants.LOGIN_SUCCESS)) {
                setWarningText("로그인 성공");
                Intent i = new Intent(getBaseContext(), ChatActivity.class);;
                startActivity(i);
                offProgressBar();
            }
            else if(data.getMsg().equals(Constants.LOGIN_FAILED)){
                setWarningText("로그인 실패");
                offProgressBar();
            }
            else if(data.getMsg().equals(Constants.SERVER_CONNECTED)){
                mIsServerConnected = true;
                setWarningText("서버 접속 성공!!");
            }
        }
    }

    public void offProgressBar(){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLoadingBar.setVisibility(View.INVISIBLE);
            }
        });
    }
    public void setWarningText(final String str) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mToast!=null)
                    mToast.cancel();

                mToast = Toast.makeText(LoginActivity.this, str, Toast.LENGTH_LONG);
                mToast.show();
                mInfo.setText(str);
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<Data> loader) {

    }

}
