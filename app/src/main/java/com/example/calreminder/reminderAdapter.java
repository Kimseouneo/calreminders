package com.example.calreminder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class reminderAdapter extends RecyclerView.Adapter<ViewHolder> implements ItemTouchHelperListener {

    private List<Component> ReminderData = null;
    private Context context;
    reminderAdapter(List<Component> list) { this.ReminderData = list; }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.reminder_item , parent , false);

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
                ((ReminderActivity)context).onComponentButtonClicked(component.Id + CalreminderData.baseId);
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
        Component component = ReminderData.get(from_position);
        ReminderData.remove(to_position);
        notifyItemMoved(from_position, to_position);
        return true;
    }

    @Override
    public void onItemSwipe(int position) {
        ReminderData.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public void onRightClick(int position, RecyclerView.ViewHolder viewHolder) {
        ReminderData.remove(position);
        notifyItemChanged(position);
    }
}
