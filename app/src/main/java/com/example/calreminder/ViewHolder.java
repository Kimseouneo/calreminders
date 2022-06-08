package com.example.calreminder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class ViewHolder extends RecyclerView.ViewHolder{
    TextView componentColor;
    TextView text;
    TextView date;
    TextView time;
    TextView place;
    Button deleteButton;
    Button notificationButton;

    ViewHolder(View item){
        super(item);

        componentColor = item.findViewById(R.id.reminder_item_componentColor);
        text = item.findViewById(R.id.reminder_item_text);
        date = item.findViewById(R.id.reminder_item_date);
        time = item.findViewById(R.id.reminder_item_time);
        place = item.findViewById(R.id.reminder_item_place);
        notificationButton = item.findViewById(R.id.reminder_item_notification);
        deleteButton = item.findViewById(R.id.reminder_Item_delete);
    }

}