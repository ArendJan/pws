package com.svshizzle.pws.smartfridge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Smartfridge extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginCheck();
        setContentView(R.layout.activity_mainactivity);
    }


    void loginCheck(){
        if(true){
           Intent intent = new Intent(Smartfridge.this, Login.class);
           Smartfridge.this.startActivity(intent);

        }
    }
}
