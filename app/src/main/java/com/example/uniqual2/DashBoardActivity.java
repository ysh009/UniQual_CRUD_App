package com.example.uniqual2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.prefs.Preferences;

public class DashBoardActivity extends AppCompatActivity {

    MyApplication myApplication;
    RecyclerView rvItems;
    static ItemsRvAdapter adapter;
    TextView addItem;
    TextView tvLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        myApplication = (MyApplication) getApplication();
        addItem = findViewById(R.id.tvAddItem);
        rvItems = findViewById(R.id.rvItems);
        tvLogout = findViewById(R.id.tvLogout);

        tvLogout.setOnClickListener(v -> {
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(DashBoardActivity.this);
                    builder.setTitle("Confirm Logout");
                    builder.setMessage("Are you sure you want to Logout ?");
                    builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(DashBoardActivity.this, "Logout successfull", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.clear();
                            editor.apply();
                            finish();

                        }

                    });
                    builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(DashBoardActivity.this,"Logout Cancle",Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.create();
                    builder.show();
        });


        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this,addItemActivity.class);
                startActivity(intent);
            }
        });
        setItemsRv();
    }

    private void setItemsRv(){
        adapter = new ItemsRvAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvItems.setLayoutManager(linearLayoutManager);
        rvItems.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter != null){
            SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.app_name),MODE_PRIVATE);
            String dataText = sharedPreferences.getString("ITEM_DATA","");

            try{
                ItemResponse itemResponse = new Gson().fromJson(dataText, new TypeToken<ItemResponse>(){}.getType());
                myApplication.getInstance().getDataList().clear();
                myApplication.getInstance().getDataList().addAll(itemResponse.getItemBeans());
                adapter.clearList();
                adapter.addToList(itemResponse.getItemBeans());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}