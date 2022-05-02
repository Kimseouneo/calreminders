package com.example.calreminder;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class EditComponent extends Fragment {

    public EditComponent() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_component, container, false);
        // Inflate the layout for this fragment

        Button cancelButton = (Button) view.findViewById(R.id.editFragment_button_cancle);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((ReminderActivity)getActivity()).getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(EditComponent.this).commit();
                fragmentManager.popBackStack();
            }
        });

        return view;
    }
}