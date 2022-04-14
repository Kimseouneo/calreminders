package com.example.calreminder;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

class CalrendarActivity extends AppCompatActivity {
    public static String format_yyyy = "yyyy";
    public static String format_Month = "MM";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Date currentTime = Calendar.getInstance().getTime();//실시간 날짜 및 시간 받기
        SimpleDateFormat format = new SimpleDateFormat(format_yyyy, Locale.getDefault());
        SimpleDateFormat format2 = new SimpleDateFormat(format_Month, Locale.getDefault());
        String current = format.format(currentTime);
        String current2 = format2.format(currentTime);
        TextView text_header = (TextView)findViewById(R.id.calendar_yearmonth);
        text_header.setText(current+"-"+current2);
    }

}
