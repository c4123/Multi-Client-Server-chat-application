package com.example.haechan.join;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by haechan on 2017-05-23.
 */

public class loginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final EditText emailText = (EditText) findViewById(R.id.emailText);
        final EditText passText = (EditText) findViewById(R.id.passText);
        TextView unichatText = (TextView) findViewById(R.id.unichatText);
        TextView messageText = (TextView) findViewById(R.id.messageText);
        CheckBox memoryCB = (CheckBox) findViewById(R.id.memoryCB);
        Button loginButton = (Button) findViewById(R.id.loginButton);
        Button joinButton = (Button) findViewById(R.id.joinButton);

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),JoinActivity.class);
                startActivity(intent);
            }
        });

        /*
        if
         로그인버튼을 누르면 서버에 있는 db정보를 가져와서 정보가 일치하면 로그인되고
        else
          없으면  회원정보가 없습니다! 라고 하는 alert창을 띄워준다
        */

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(loginActivity.this);
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
        memoryCB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name = emailText.getText().toString();
                passText.setText(name);
            }


        });


    }
}
