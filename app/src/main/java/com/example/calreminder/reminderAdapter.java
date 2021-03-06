package com.example.calreminder;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;


public class reminderAdapter extends RecyclerView.Adapter<ViewHolder> implements ItemTouchHelperListener {

    public static List<Component> ReminderData;
    private Context context;
    reminderAdapter(List<Component> list) {
        ReminderData = list;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.reminder_item , parent , false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Component component = ReminderData.get(position);
        holder.text.setText(component.text);
        holder.date.setText(component.date);
        holder.time.setText(component.time);
        holder.place.setText(component.place);
        holder.componentColor.setBackgroundColor(component.color);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                    ((ReminderActivity)context).onComponentButtonClicked(component.Id + CalreminderData.baseId);
                else if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                    ((ReminderActivity)context).onComponentButtonClickedLand(component.Id + CalreminderData.baseId);
                else
                    Log.d("EXCEPTION", "Can't get orientation");
            }
        });
        holder.itemView.setTag(new Boolean(false));
        if (component.isNotified) {
            holder.notificationButton.setBackground(context.getDrawable(R.drawable.ic_action_notifi_active));
        }
        else {
            holder.notificationButton.setBackground(context.getDrawable(R.drawable.ic_action_notifi_off));
        }
        holder.notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isNotified = component.isNotified;
                AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(context, AlertReceiver.class);
                intent.putExtra("Title",component.text);
                intent.putExtra("Text",component.date + " " + component.time);
                intent.putExtra("Id",component.Id);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context,component.Id,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                if(isNotified) {
                    // ????????? ????????? ????????? ??????
                    alarmManager.cancel(pendingIntent);
                    CalreminderData.componentDataDao.updateNotified(component.Id, component.isNotified = (!isNotified));

                    holder.notificationButton.setBackground(context.getDrawable(R.drawable.ic_action_notifi_off));
                    Toast toast = Toast.makeText(context,"????????? ????????????.",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    // ????????? ???????????? ?????? ????????? ??????
                    Calendar calendar = Calendar.getInstance();
                    if (component.date.equals("")) {
                        Toast toast = Toast.makeText(context,"????????? ????????? ????????????",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else {
                        String date[] = component.date.split("/");
                        calendar.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]));
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        CalreminderData.componentDataDao.updateNotified(component.Id, component.isNotified = (!isNotified));
                        // ?????? ?????? ????????????

                        holder.notificationButton.setBackground(context.getDrawable(R.drawable.ic_action_notifi_active));
                        Toast toast = Toast.makeText(context,"????????? ?????????????????????.",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(context, AlertReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context,component.Id,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.cancel(pendingIntent);

                ReminderList.touchHelperCallback.removePreviousClamp(ReminderList.recyclerView);

                CalreminderData.componentDataDao.deleteComponent(ReminderData.get(holder.getLayoutPosition()).Id);
                ReminderData.remove(holder.getLayoutPosition());
                notifyItemRemoved(holder.getLayoutPosition());
                MonthOfListAdapter.componentArrayList = CalreminderData.componentDataDao.getHasDateComponent();
                if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                    CalendarMonth.adapter.notifyDataSetChanged();
                v.setEnabled(false);
            }
        });
    }

    // getItemCount() - ?????? ????????? ?????? ??????.
    @Override
    public int getItemCount() {
        return ReminderData.size() ;
    }

    @Override
    public boolean onItemMove(int from_position, int to_position) {
        notifyItemMoved(from_position, to_position);
        return true;
    }

    @Override
    public void onItemSwipe(int position) {

    }


    @Override
    public void onRightClick(int position, RecyclerView.ViewHolder viewHolder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("??????");
        builder.setMessage("?????????????????????????");
        builder.setPositiveButton("???", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                CalreminderData.componentDataDao.deleteComponent(ReminderData.get(position).Id);
                ReminderData.remove(position);
                Toast.makeText(context.getApplicationContext(), "?????????????????????", Toast.LENGTH_SHORT).show();
                notifyItemChanged(position);
            }
        });

        builder.setNegativeButton("?????????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.show();
    }
}
