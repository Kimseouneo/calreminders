package com.example.calreminder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MonthOfListAdapter extends RecyclerView.Adapter<Holder> {
    ArrayList<String> list;
    public MonthOfListAdapter(ArrayList<String> list){
        this.list = list;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {
        holder.tx.setText(list.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    v.setBackgroundColor(Color.GRAY);
                    Thread.sleep(10);
                } catch (InterruptedException e) {

                }
                Log.d("클릳되었다.", list.get(position));
            }
            });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}

