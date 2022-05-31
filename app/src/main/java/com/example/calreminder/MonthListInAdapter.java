package com.example.calreminder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MonthListInAdapter extends RecyclerView.Adapter<MonthListInHolder>{
    ArrayList<Component> components;
    public MonthListInAdapter(ArrayList<Component> components){
        this.components = components;
    }

    @NonNull
    @Override
    public MonthListInHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.monthlistin_item, parent, false);
        return new MonthListInHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthListInHolder holder, @SuppressLint("RecyclerView") int position){
        holder.monthListIn.setText(components.get(position).text);
    }

    @Override
    public int getItemCount(){
        return components.size();
    }
}
