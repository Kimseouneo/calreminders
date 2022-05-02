package com.example.calreminder;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatButton;

public class ReminderComponentView extends AppCompatButton {
    //Reminder의 각 항목을 나타내는 버튼 뷰
    public ReminderComponentView(Context context) {
        super(context);
    }

    public ReminderComponentView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }

}
