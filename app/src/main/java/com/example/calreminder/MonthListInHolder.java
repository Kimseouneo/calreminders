package com.example.calreminder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MonthListInHolder extends RecyclerView.ViewHolder {
    TextView monthListIn;
    public MonthListInHolder(@NonNull View itemView){
        super(itemView);
        monthListIn = itemView.findViewById(R.id.monthlistitem);
    }
}
