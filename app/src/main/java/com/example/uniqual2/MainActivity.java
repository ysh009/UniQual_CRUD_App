package com.example.uniqual2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private TextView btn;
    private EditText email;
    private EditText password;
    SharedPreferences shp;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.loginBtn);
        email = findViewById(R.id.emailText);
        password = findViewById(R.id.passwordText);


        btn.setEnabled(false);

        btn.setBackgroundResource(R.drawable.ic_bg_rd_gray);
        shp = getSharedPreferences(getResources().getString(R.string.app_name) ,MODE_PRIVATE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailValue = email.getText().toString();
                String passwordValue = password.getText().toString();

                if (Patterns.EMAIL_ADDRESS.matcher(emailValue).matches() && passwordValue.length() >= 8) {

                    SharedPreferences.Editor editor = shp.edit();
                    editor.putString(Constants.EMAIL, emailValue);
                    editor.putString(Constants.PASSWORD, passwordValue);
                    editor.apply();

                    Intent intent = new Intent(MainActivity.this, DashBoardActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "WelCome", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                }

            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (Patterns.EMAIL_ADDRESS.matcher(editable).matches()) {
                    if (password.getText().toString().length()>=8) {
                        btn.setEnabled(true);
                        btn.setBackgroundResource(R.drawable.ic_bg_rd_red);
                    }
                    else {
                        btn.setEnabled(false);
                    }
                }
                else{
                    btn.setEnabled(false);
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }


            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length()>=8) {
                    if (Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                        btn.setEnabled(true);
                        btn.setBackgroundResource(R.drawable.ic_bg_rd_red);
                    } else {
                        btn.setEnabled(false);
                    }
                } else {
                    btn.setEnabled(false);
                }
            }
        });
    }
}
