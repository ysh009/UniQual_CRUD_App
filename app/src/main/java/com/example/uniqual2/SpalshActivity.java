package com.example.uniqual2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;

public class SpalshActivity extends AppCompatActivity {

    SharedPreferences shp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);

        shp = getSharedPreferences(getResources().getString(R.string.app_name),MODE_PRIVATE);
        String Email = shp.getString(Constants.EMAIL,"");
        String Password = shp.getString(Constants.PASSWORD,"");

        if(Patterns.EMAIL_ADDRESS.matcher(Email).matches() && Password.length()>=8){

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SpalshActivity.this, DashBoardActivity.class);
                    startActivity(intent);
                    finish();
                }
            },1500);
        }
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SpalshActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            },1500);
        }
    }
}