package com.example.calreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ReminderActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        ImageButton addButton = (ImageButton) findViewById(R.id.listFragment_button_add);


        FragmentManager fm = getSupportFragmentManager();
        ReminderList listFragment = new ReminderList();
        fm.beginTransaction().add(R.id.reminderListFragment, listFragment).commit();
    }

    public void onAddButtonClicked(View view) {
        EditComponent editEditComponentFragment = new EditComponent();
        Bundle args = new Bundle();
        editEditComponentFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.reminderListFragment, editEditComponentFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}