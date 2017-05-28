package bumbums.client_android;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import aboullaite.Data;
import aboullaite.LoginData;
import aboullaite.util.Constants;

public class JoinActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Data> {

    private Client mClient;
    private final int JOIN_LOADER = 3;
    private EditText et_mail;
    private EditText et_passwdconfirm;
    private EditText et_passwd;
    private Button btn_join;
    private CheckBox cb_agree;
    private CheckBox cb_agree2;
    private TextView tv_warning;
    private ProgressBar mLoadingBar;
    private Toast mToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        mClient = Client.getInstance();

        et_mail = (EditText) findViewById(R.id.et_email);
        et_passwdconfirm = (EditText) findViewById(R.id.et_passwdconfirm);
        et_passwd = (EditText) findViewById(R.id.et_passwd);
        btn_join = (Button) findViewById(R.id.btn_join);
        cb_agree = (CheckBox) findViewById(R.id.cb_agree);
        cb_agree2 = (CheckBox) findViewById(R.id.cb_agree2);
        tv_warning = (TextView) findViewById(R.id.tv_warning);
        mLoadingBar = (ProgressBar)findViewById(R.id.pb_loading);

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = et_passwd.getText().toString();
                String pwd_confirm = et_passwdconfirm.getText().toString();
                String emailAddress = et_mail.getText().toString();

                if (emailAddress.length() == 0) {
                    AlertDialog.Builder email = new AlertDialog.Builder(JoinActivity.this);
                    email.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    email.setMessage("Email을 입력해주세요.");
                    email.show();
                }else if(!checkEmail(emailAddress)){
                    Toast.makeText(JoinActivity.this,"이메일 주소가 아닙니다.",Toast.LENGTH_LONG).show();
                }
                else if(!isDonggukEmail(emailAddress)){
                    Toast.makeText(JoinActivity.this,"동국대학교 이메일 주소가 아닙니다.",Toast.LENGTH_LONG).show();
                }
                else if (pwd.length() == 0 || pwd_confirm.length() == 0) {
                    AlertDialog.Builder passcheck = new AlertDialog.Builder(JoinActivity.this);
                    passcheck.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    passcheck.setMessage("비밀번호를 입력해주세요.");
                    passcheck.show();
                } else if (!cb_agree.isChecked() || !cb_agree2.isChecked()) {
                    AlertDialog.Builder check = new AlertDialog.Builder(JoinActivity.this);
                    check.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    check.setMessage("약관에 동의해주세요.");
                    check.show();
                }

                else {
                    mLoadingBar.setVisibility(View.VISIBLE);
                    final String id = et_mail.getText().toString();
                    final String pw = et_passwd.getText().toString();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            LoginData loginData = new LoginData(id, pw, Constants.TYPE_REGISTER);
                            mClient.sendMessage(loginData);
                        }
                    }).start();

                }
            }


        });

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<Data> loginLoader = loaderManager.getLoader(JOIN_LOADER);
        if (loginLoader == null) {
            loaderManager.initLoader(JOIN_LOADER, null, this).forceLoad();
        } else {
            loaderManager.restartLoader(JOIN_LOADER, null, this);
        }

    }

    @Override
    public Loader<Data> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Data>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                mClient.setListener(new Client.OnMessageReceived() {
                    @Override
                    public void messageReceived(Data data) {
                        deliverResult(data);
                        Log.d("#####", "들어온 데이터" + data.getMsg());
                    }
                });
                Log.d("#####", "onstartLoding");
            }

            @Override
            public Data loadInBackground() {
                Log.d("#####", "doinBack");


                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Data> loader, Data data) {
        if (data != null) {
            int type = data.getType();
            String serverMsg = data.getMsg();

            if (serverMsg.equals(Constants.REGISTER_FAIL_ID)) {
                setWarningText("아이디가 이미 존재합니다.");
            } else if (serverMsg.equals(Constants.REGISTER_WAITING_AUTHCODE)) {
                setWarningText("서버에서 인증번호를 발송하였습니다.");
                showAuthInput();
            } else if (serverMsg.equals(Constants.REGISTER_FAIL_AUTHCODE)) {
                setWarningText("인증에 실패하였습니다.");
            } else if (serverMsg.equals(Constants.REGISTER_SUCCESS)) {
                setWarningText( "가입이 완료되었습니다.");
                finish();
            }

             offProgressBar();
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

                mToast = Toast.makeText(JoinActivity.this, str, Toast.LENGTH_LONG);
                mToast.show();
                tv_warning.setText(str);
            }
        });
    }


    @Override
    public void onLoaderReset(Loader<Data> loader) {

    }

    public void showAuthInput() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder welcome = new AlertDialog.Builder(JoinActivity.this);
                final EditText authCode = new EditText(JoinActivity.this);
                authCode.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                welcome.setView(authCode);
                welcome.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                     setWarningText("인증코드를 확인중입니다. 잠시만 기다려주세요");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mClient.sendMessage(new Data(Constants.TYPE_MSG, authCode.getText().toString(), null));
                            }
                        }).start();
                        dialog.dismiss(); //확인버튼누르면 Alert창이 버려짐
                    }
                });
                welcome.setNegativeButton("가입취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setWarningText("가입이 취소되었습니다.");
                        //서버에 로그인 취소 신호 보냄
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mClient.sendMessage(new Data(Constants.TYPE_MSG, Constants.REGISTER_CANCEL, null));
                            }
                        }).start();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                });
                welcome.setMessage("인증코드를 입력해주세요.");
                welcome.show();
            }
        });

    }

    /**
     * 이메일 포맷 체크
     * @param email
     * @return
     */
    public boolean checkEmail(String email){

        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        boolean isNormal = m.matches();
        return isNormal;

    }
    public boolean isDonggukEmail(String email){
        String domain;
        int i = email.indexOf("@");
        domain = email.substring(i+1);

        if(domain.equals("dongguk.edu")){
            return true;
        }
        else{
            return false;
        }
    }


}
