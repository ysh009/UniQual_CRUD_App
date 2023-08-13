package com.example.uniqual2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Objects;

public class addItemActivity extends AppCompatActivity {

    private TextView tvBack;
    private TextView tvSave;
    private TextView tvDelete;
    EditText edTitle;
    EditText edPrice;
    private TextView tvAddItem;
    boolean isEdit;
    MyApplication myApplication;
    ItemBean editData;
    String itemBeanData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        myApplication = (MyApplication) getApplication();

        tvBack = findViewById(R.id.tvBack);
        tvSave = findViewById(R.id.tvSave);
        edTitle = findViewById(R.id.edTitle);
        edPrice = findViewById(R.id.edPrice);
        tvAddItem = findViewById(R.id.tvAddItem);
        tvDelete = findViewById(R.id.tvDelete);


        tvBack.setOnClickListener(v -> finish());

        isEdit = getIntent().hasExtra(Constants.EDIT_DATA);

        if (isEdit) {
            itemBeanData = getIntent().getStringExtra(Constants.EDIT_DATA);
            tvDelete.setVisibility(View.VISIBLE);
            editData = new Gson().fromJson(itemBeanData, new TypeToken<ItemBean>() {
            }.getType());
            tvAddItem.setText("Edit Item");
            edTitle.setText(editData.getTitle());
            edPrice.setText(editData.getPrice());
        }

        tvSave.setOnClickListener(v -> {
            if (isValid()) {
                if (isEdit) {
                    for (int i = 0; i < myApplication.getInstance().getDataList().size(); i++) {
                        ItemBean temp = myApplication.getInstance().getDataList().get(i);
                        if (Objects.equals(temp.getId(), editData.getId())) {
                            editData.setTitle(edTitle.getText().toString());
                            editData.setPrice(edPrice.getText().toString());
                            myApplication.arrItems.set(i, editData);
                            DashBoardActivity.adapter.notifyItemChanged(i);

                            ItemResponse itemResponse1 = new ItemResponse();
                            itemResponse1.setItemBeans(myApplication.getInstance().getDataList());

                            SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.app_name), MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putString("ITEM_DATA", new Gson().toJson(itemResponse1));
                            myEdit.apply();
                            finish();
                        }
                    }
                } else {
                    ItemBean itemBean = new ItemBean();
                    itemBean.setPrice(edPrice.getText().toString());
                    itemBean.setTitle(edTitle.getText().toString());
                    itemBean.setId(System.currentTimeMillis() / 1000);

                    myApplication.getInstance().addData(itemBean);

                    ItemResponse itemResponse = new ItemResponse();
                    itemResponse.setItemBeans(myApplication.getInstance().getDataList());

                    SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.app_name), MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("ITEM_DATA", new Gson().toJson(itemResponse));
                    myEdit.apply();
                    finish();
                }
            } else {
                Toast.makeText(addItemActivity.this, "Please Enter Details", Toast.LENGTH_SHORT).show();
            }
        });
        tvDelete.setOnClickListener(view -> {
            for (int i = 0; i < myApplication.getInstance().getDataList().size(); i++) {
                ItemBean data = myApplication.getInstance().getDataList().get(i);
                if (Objects.equals(data.getId(), editData.getId())) {
                    try {
                        myApplication.arrItems.remove(i);
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    ItemResponse itemResponse = new ItemResponse();
                    itemResponse.setItemBeans(myApplication.getInstance().getDataList());
                    ItemBean editData1 = new Gson().fromJson(itemBeanData, new TypeToken<ItemBean>() {
                    }.getType());

                    SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.app_name), MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();

                    if (data.getId() == editData1.getId()) {
                        myEdit.remove(String.valueOf(data.getId()));
                    }

                    myEdit.putString("ITEM_DATA", new Gson().toJson(itemResponse));
                    myEdit.apply();

                    finish();
                }
            }
        });
    }

    public boolean isValid() {
        boolean validTitle;
        boolean validPrice;
        edTitle = findViewById(R.id.edTitle);
        edPrice = findViewById(R.id.edPrice);

        validTitle = !edTitle.getText().toString().isEmpty();
        validPrice = !edPrice.getText().toString().isEmpty();

        return validTitle && validPrice;
    }
}