package bumbums.client_android;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import aboullaite.Data;
import aboullaite.LoginData;
import aboullaite.util.Constants;

public class LoginActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Data> {

    private Client mClient;
    private EditText mEmail;
    private EditText mPasswd;
    private EditText mServerIP;
    private Button mLoginBtn;
    private Button mJoinBtn;
    private Button mServerBtn;
    private final int LOGIN_LOADER = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmail = (EditText) findViewById(R.id.et_email);
        mPasswd = (EditText) findViewById(R.id.et_passwd);
        mServerIP = (EditText) findViewById(R.id.et_ipaddress);
        mJoinBtn = (Button) findViewById(R.id.btn_join);
        mLoginBtn = (Button) findViewById(R.id.btn_login);
        mServerBtn = (Button) findViewById(R.id.btn_serverAccess);

        // connect to the server
        final LoaderManager loaderManager = getSupportLoaderManager();
        final Loader<Data> loginLoader = loaderManager.getLoader(LOGIN_LOADER);

        mClient = Client.getInstance();

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
                final String id = mEmail.getText().toString();
                final String passwd = mPasswd.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LoginData loginData = new LoginData(id, passwd, Constants.TYPE_LOGIN);
                        mClient.sendMessage(loginData);
                    }
                }).start();

            }
        });

       /* mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                alert.setPositiveButton("FAILED",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.setMessage("회원정보가 없습니다!");
                alert.show();
            }
        });
       */

        mJoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
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
            }

            @Override
            public Data loadInBackground() {
                Log.d("#####", "doinBack");
                mClient.setListener(new Client.OnMessageReceived() {
                    @Override
                    public void messageReceived(Data data) {
                        deliverResult(data);
                    }
                });
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
                Intent i = new Intent(getBaseContext(), ChatActivity.class);;
                startActivity(i);

            }
            else if(data.getMsg().equals(Constants.LOGIN_FAILED)){

            }
        }
    }



    @Override
    public void onLoaderReset(Loader<Data> loader) {

    }

}
