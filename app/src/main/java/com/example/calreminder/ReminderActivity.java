package com.example.calreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class ReminderActivity extends AppCompatActivity{
    //Main Activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);


        CalreminderData.data = getSharedPreferences("com.example.calreminder", MODE_PRIVATE);
        CalreminderData.id = getSharedPreferences("com.example.calreminder.id", MODE_PRIVATE);
        // id 값 지정 어플 전체애서 한번만 실행됨
        if (!CalreminderData.id.getAll().containsKey("ID"))
            CalreminderData.id.edit().putInt("ID",0X8000 + 1000).apply();

        ImageButton addButton = (ImageButton) findViewById(R.id.listFragment_button_add);


        FragmentManager fm = getSupportFragmentManager();
        ReminderList listFragment = new ReminderList();
        fm.beginTransaction().add(R.id.reminderListFragment, listFragment).commit();
    }

    public void onAddButtonClicked(View view) {
        //Reminder Fragment에서 +버튼을 눌렀을때 실행되는 코드
        EditComponent editEditComponentFragment = new EditComponent();
        Bundle args = new Bundle();
        editEditComponentFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.reminderListFragment, editEditComponentFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onComponentButtonClicked(View view, String text, int id) {
        // Reminder Fragment에서 항목을 눌렀을때 실행되는 코드
        // 미완성 현재 텍스트만 넘겨줌
        EditComponent editEditComponentFragment = new EditComponent();
        Bundle args = new Bundle();
        args.putString("TEXT",text);
        args.putInt("ID", id);

        editEditComponentFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.reminderListFragment, editEditComponentFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}