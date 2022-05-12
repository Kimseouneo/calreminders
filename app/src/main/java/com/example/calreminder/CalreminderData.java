package com.example.calreminder;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class CalreminderData {
    //리마인더와 캘린더 데이터를 관리하는 클래스

    // 리마인더 항목들을 저장하는 데이터 key: id / value : JSON ARRAY {텍스트, 날짜, 시간, 장소}
    public static SharedPreferences data;
    // 새로운 항목을 추가할때 부여하는 id를 저장하는 데이터
    public static SharedPreferences id;

    CalreminderData() {

    }

    public static ArrayList<String> jsonToArrayList(String json) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            Log.d("123456789123456789",json);
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                String string = jsonArray.optString(i);
                arrayList.add(string);
                Log.d("132456789123546789",arrayList.get(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("123456789123546789",arrayList.toString());
        return arrayList;
    }

    public static JSONArray ArrayListToJson(ArrayList<String> arrayList) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < arrayList.size(); i++) {
            jsonArray.put(arrayList.get(i));
        }
        return jsonArray;
    }
}
