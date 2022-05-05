package com.example.calreminder;

import android.content.SharedPreferences;
import android.widget.Button;

import java.util.ArrayList;

public class CalreminderData {
    //리마인더와 캘린더 데이터를 관리하는 클래스
    
    // 리마인더 항목들을 저장하는 데이터 key: id / value : text, color 등 JSON ARRAY 형식으로 저장
    public static SharedPreferences data;
    public static SharedPreferences id;

    public static int currentId = -1;
    public static String text = null;
    CalreminderData() {

    }
}
