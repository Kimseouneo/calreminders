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
import android.widget.LinearLayout;

import org.json.JSONArray;

import java.util.ArrayList;

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
                String text = editText.getText().toString();
                Integer id;
                Bundle args = getArguments();
                ArrayList<String> arrayList;
                JSONArray jsonArray;

                if (args.containsKey("ID")) {
                    // 이미 있는 항목을 편집한 경우
                    id = args.getInt("ID");
                    arrayList = CalreminderData.jsonToArrayList(CalreminderData.data.getString(id.toString(),null));
                    arrayList.set(0, editText.getText().toString());
                    jsonArray = CalreminderData.ArrayListToJson(arrayList);
                }
                else {
                    // 새로운 항목을 저장한 경우
                    id = CalreminderData.id.getInt("ID", -1);
                    CalreminderData.id.edit().putInt("ID", (CalreminderData.id.getInt("ID",-1)) + 1).apply();
                    jsonArray = new JSONArray();
                    jsonArray.put(editText.getText());
                }
                CalreminderData.data.edit().putString(id.toString(),jsonArray.toString()).apply();

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
        if (args.containsKey("ID")) {
            //삭제버튼 생성
            Button buttonDelete = (Button) getActivity().findViewById(R.id.editFragment_button_delete);
            buttonDelete.setVisibility(View.VISIBLE);
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //삭제버튼 클릭시 데이터와 리스트에서 해당하는 항목 삭제
                    Integer id = args.getInt("ID");
                    CalreminderData.data.edit().remove(id.toString()).apply();

                    FragmentManager fragmentManager = ((ReminderActivity)getActivity()).getSupportFragmentManager();
                    fragmentManager.beginTransaction().remove(EditComponent.this).commit();
                    fragmentManager.popBackStack();
                }
            });

            EditText editText = (EditText) getActivity().findViewById(R.id.editFragment_editText_content);
            ArrayList<String> arrayList = CalreminderData.jsonToArrayList(CalreminderData.data.getString(Integer.toString(args.getInt("ID")),null));
            editText.setText(arrayList.get(0));
        }
        else {
            Button buttonDelete = (Button) getActivity().findViewById(R.id.editFragment_button_delete);
            buttonDelete.setVisibility(View.GONE);
        }
    }
}