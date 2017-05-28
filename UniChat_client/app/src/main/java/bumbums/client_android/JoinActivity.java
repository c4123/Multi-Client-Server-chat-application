package bumbums.client_android;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        final EditText et_mail = (EditText) findViewById(R.id.et_email);
        final EditText et_passwdconfirm= (EditText) findViewById(R.id.et_passwdconfirm);
        final EditText et_passwd = (EditText) findViewById(R.id.et_passwd);
        Button btn_certification = (Button) findViewById(R.id.btn_certification);
        Button btn_join = (Button) findViewById(R.id.btn_join);
        final CheckBox cb_agree = (CheckBox) findViewById(R.id.cb_agree);
        final CheckBox cb_agree2 = (CheckBox) findViewById(R.id.cb_agree2);
        final TextView tv_warning = (TextView) findViewById(R.id.tv_warning);
        final String str1 = et_passwd.getText().toString();
        final String str2 = et_passwdconfirm.getText().toString();
        final String str3 = et_mail.getText().toString();

        btn_certification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str1 = et_passwd.getText().toString();
                String str2 = et_passwdconfirm.getText().toString();
                //passTextjoin.getText().equals(passcheckjoin.getText())
                if(str1.equals(str2)) // str1과 str2의 텍스트가 같으면 출력하지 않음
                {
                    tv_warning.setText(" ");
                }
                else // str1과 str2의 텍스트가 다르면 밑에 텍스트를 출력
                {
                    tv_warning.setText("비밀번호가 일치하지 않습니다.");
                }

            }
        });
        btn_join.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String str1 = et_passwd.getText().toString();
                String str2 = et_passwdconfirm.getText().toString();
                String str3 = et_mail.getText().toString();


                if(str3.length() == 0 )
                {
                    AlertDialog.Builder email = new AlertDialog.Builder(JoinActivity.this);
                    email.setPositiveButton("FAILED",new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    });
                    email.setMessage("Email을 입력해주세요.");
                    email.show();
                }
                else if(str1.length() == 0 || str2.length() == 0)
                {
                    AlertDialog.Builder passcheck = new AlertDialog.Builder(JoinActivity.this);
                    passcheck.setPositiveButton("FAILED",new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    });
                    passcheck.setMessage("비밀번호를 입력해주세요.");
                    passcheck.show();
                }
                else if(!cb_agree.isChecked() || !cb_agree2.isChecked())
                {
                    AlertDialog.Builder check = new AlertDialog.Builder(JoinActivity.this);
                    check.setPositiveButton("FAILED",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    check.setMessage("약관에 동의해주세요.");
                    check.show();
                }
                else
                {
                    AlertDialog.Builder welcome = new AlertDialog.Builder(JoinActivity.this);
                    welcome.setPositiveButton("확인",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss(); //확인버튼누르면 Alert창이 버려짐
                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                    welcome.setMessage("축하드립니다! 회원가입이 완료 되었습니다.");
                    welcome.show();
                }
            }




        });

    }
}
