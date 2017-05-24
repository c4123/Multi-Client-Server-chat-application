package com.example.haechan.join;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        EditText emailTextjoin = (EditText) findViewById(R.id.emailTextjoin);
        EditText passcheckjoin= (EditText) findViewById(R.id.passcheckTextjoin);
        EditText passTextjoin = (EditText) findViewById(R.id.passTextjoin);
        Button certificateButton = (Button) findViewById(R.id.certificateButton);
        Button joinbutton2 = (Button) findViewById(R.id.joinButton2);
        CheckBox agreeCB = (CheckBox) findViewById(R.id.agreeCB);
        CheckBox agree2CB = (CheckBox) findViewById(R.id.agree2CB);
    }
}
