package com.example.calreminder;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.shadow.ShadowRenderer;

import java.util.EventListener;
import java.util.EventObject;
import java.util.List;


public class reminderAdapter extends RecyclerView.Adapter<ViewHolder> implements ItemTouchHelperListener {

    private List<Component> ReminderData = null;
    private Context context;
    private View view;
    reminderAdapter(List<Component> list) { this.ReminderData = list; }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.reminder_item , parent , false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Component component = ReminderData.get(position);
        holder.textView.setText(component.text);
        holder.Time.setText(component.date);
        holder.textView.setBackgroundColor(component.color);

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

        view.findViewById(R.id.reminderItem_button_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalreminderData.componentDataDao.deleteComponent(ReminderData.get(position).Id);
                ReminderData.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
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
        builder.setTitle("삭제");
        builder.setMessage("삭제하시겠습니까?");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                CalreminderData.componentDataDao.deleteComponent(ReminderData.get(position).Id);
                ReminderData.remove(position);
                Toast.makeText(context.getApplicationContext(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
                notifyItemChanged(position);
            }
        });

        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.show();
    }
}
