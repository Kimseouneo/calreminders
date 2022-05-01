package com.example.calreminder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ReminderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        ImageButton addButton = (ImageButton) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentContainerView editFragment = (FragmentContainerView) findViewById(R.id.reminderEditFragment);
                editFragment.setVisibility(View.VISIBLE);
                ImageButton addButton = (ImageButton) findViewById(R.id.addButton);
                addButton.setVisibility(View.INVISIBLE);

                FragmentManager fm = getSupportFragmentManager();
                Component blankFragment = new Component();
                fm.beginTransaction().add(R.id.reminderEditFragment, blankFragment).commit();
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        ReminderList listFragment = new ReminderList();
        fm.beginTransaction().add(R.id.reminderListFragment, listFragment).commit();
    }

}