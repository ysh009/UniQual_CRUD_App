package com.example.uniqual2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ItemsRvAdapter extends RecyclerView.Adapter<ItemsRvAdapter.ItemViewHolder> {
    ArrayList<ItemBean> listdata = new ArrayList<>();
    Context context;

    public ItemsRvAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View viewItem= LayoutInflater.from(parent.getContext()).inflate(R.layout.add_item_card,parent,false);
        return new ItemViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ItemBean data = listdata.get(position);
        String priceText = "$" + data.getPrice();

        holder.tvDescription.setText(data.getTitle());
        holder.price.setText(priceText);

        holder.itemView.setOnClickListener(v->{
            Intent intent = new Intent(context,addItemActivity.class);
            intent.putExtra(Constants.EDIT_DATA, new Gson().toJson(data));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public void addData(ItemBean itemBean){
        int newPos = listdata.size();
        listdata.add(itemBean);
        notifyItemInserted(newPos);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clearList(){
        listdata.clear();
        notifyDataSetChanged();
    }

    public void addToList(ArrayList<ItemBean> newDataList) {
        if (newDataList == null) {
            newDataList = new ArrayList<>();
        }
        int newPosition = listdata.size();
        listdata.addAll(newDataList);
        notifyItemRangeInserted(newPosition, newDataList.size());
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView tvDescription,price;
        public ItemViewHolder(@NonNull View itemView){
            super(itemView);

            tvDescription=itemView.findViewById(R.id.tvDescription);
            price=itemView.findViewById(R.id.price);
        }
    }
}