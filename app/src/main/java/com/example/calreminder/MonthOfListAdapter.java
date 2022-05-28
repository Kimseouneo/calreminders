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
import java.util.Calendar;


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
        holder.day.setText(list.get(position));
        holder.schedule.setText("일정");
        if(position % 7 == 0)
            holder.day.setTextColor(Color.BLUE);
        if((position+1) % 7 == 0)
            holder.day.setTextColor(Color.RED);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    v.setBackgroundColor(Color.GRAY);
                    Thread.sleep(10);
                } catch (InterruptedException e) {

                }
                Log.d("클릭되었다.", holder.schedule.getText().toString());
            }
            });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}

