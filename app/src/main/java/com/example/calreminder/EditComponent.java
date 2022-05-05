package com.example.calreminder;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class EditComponent extends Fragment {
    //리마인더 항목을 수정할때 나타나는 Fragment
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

        // 취소 버튼
        Button cancelButton = (Button) view.findViewById(R.id.editFragment_button_cancle);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((ReminderActivity)getActivity()).getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(EditComponent.this).commit();
                fragmentManager.popBackStack();
            }
        });

        // 저장 버튼
        Button saveButton = (Button) view.findViewById(R.id.editFragment_button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText) getActivity().findViewById(R.id.editFragment_editText_content);
                CalreminderData.text = editText.getText().toString();

                Bundle args = getArguments();
                Log.d("????????????????????", args.toString());
                if (args.containsKey("ID")) {
                    // 이미 있는 항목을 편집한 경우
                    CalreminderData.currentId = args.getInt("ID");
                }
                else {
                    // 새로운 항목을 저장한 경우
                    CalreminderData.currentId = CalreminderData.id.getInt("ID", -1);
                    CalreminderData.id.edit().putInt("ID", (CalreminderData.id.getInt("ID",-1)) + 1).apply();
                    Log.d("?????????????????",Integer.toString(CalreminderData.id.getInt("ID",-1)));
                }

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(EditComponent.this).commit();
                fragmentManager.popBackStack();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // 만약 새로추가 버튼이 아닌 이미 존재하는 Component를 선택할 경우 값을 대입해줌
        Bundle args = getArguments();
        if (args != null) {
            EditText editText = (EditText) getActivity().findViewById(R.id.editFragment_editText_content);
            editText.setText(args.getString("TEXT"));
        }
    }
}