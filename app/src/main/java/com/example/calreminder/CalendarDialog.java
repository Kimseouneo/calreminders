package com.example.calreminder;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalendarDialog extends Dialog {
    //캘린더 다이얼로그
    ArrayList<Component> components;
    ToDoOfCalendarAdapter adapter;
    RecyclerView recyclerView;
    Context mContext;
    TextView bottomText;
    ItemTouchHelper helper;
    public CalendarDialog(@NonNull Context context, ArrayList<Component> components){
        super(context);
        mContext = context;
        this.components = components;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){

        Log.d("!!!!", "다이얼로그 오픈");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomText = findViewById(R.id.closeOrDelete);
        bottomText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        //주석 추가
        adapter = new ToDoOfCalendarAdapter(this.getContext(), components);
        adapter.setOnItemClickListener(new ToDoOfCalendarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                dismiss();
                ((ReminderActivity)mContext).onComponentButtonClicked(components.get(pos).Id + CalreminderData.baseId);
                adapter.notifyItemChanged(pos);
            }
        });
        recyclerView = findViewById(R.id.todolist);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
        helper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
        helper.attachToRecyclerView(recyclerView);
    }

    private void setUpRecyclerView(){
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                helper.onDraw(c,parent, state);
            }
        });
    }


}
