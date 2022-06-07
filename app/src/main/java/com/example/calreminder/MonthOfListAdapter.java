package com.example.calreminder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//더블클릭 하면 리사이클러뷰 등장하는 걸 아직 못시킴
//현재 onbindviewholder에서 어레이리스트를 선언하고 거기에 데이터를 저장하면 값이 저장된다는 사실을 확인했음
//이걸 이용하여 다이얼로그를 만들고 다이얼로그 안에 리사이클러뷰를 넣어볼 예정
//삭제버튼 테스트 성공! 커스텀 다이얼로그 제작해야함
public class MonthOfListAdapter extends RecyclerView.Adapter<CalendarHolder> {
    //캘린더를 구현하기 위해 사용할 리사이클러뷰의 어댑터
    private ArrayList<String> list;
    private CalendarDialog dialog;
    private Calendar check = Calendar.getInstance();
    private Calendar check2 = Calendar.getInstance();
    private Context context;
    private View saveData = null;
    long checktime = 0;
    int month;
    int year;
    List<Component> componentArrayList;
    int[] monthList;
    Calendar checkCalendar = Calendar.getInstance();
    public MonthOfListAdapter(Context context, ArrayList<String> list, int month, int[] monthList){
        this.context = CalendarFragment.Calendarcontext;
        this.list = list;
        this.month = month;
        this.year = CalendarFragment.year;
        this.monthList = monthList;
        check.set(year, month-1, 1);
        check2.set(year, month-1, monthList[month-1]);
        componentArrayList = CalreminderData.componentDataDao.getHasDateComponent();
    }
    @NonNull
    @Override
    public CalendarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_list, parent, false);
        return new CalendarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarHolder holder, @SuppressLint("RecyclerView") int position) {
        //리사이클러뷰의 아이템을 설정하는 메소드
        ArrayList<Component> toDo = new ArrayList<>();
        holder.day.setText(list.get(position));
        if(position < check.get(Calendar.DAY_OF_WEEK) - 1) {
            holder.day.setTextColor(Color.LTGRAY);
        }
        if(position >= list.size() - (7 - check2.get(Calendar.DAY_OF_WEEK)))
            holder.day.setTextColor(Color.LTGRAY);
        Log.d("!!!!!!!!!!!!!!!!!!", "만들어짐");
        if(!(position < check.get(Calendar.DAY_OF_WEEK) - 1) && !(position >= list.size() - (7 - check2.get(Calendar.DAY_OF_WEEK)))) {
            try {
                for (int i = 0; i < componentArrayList.size(); i++) {
                    if (componentArrayList.get(i).date.equals(Integer.toString(year) + '/' + Integer.toString(month) + '/' + list.get(position))) {
                        //반복문 순회를 통해서 componentArrayList에 있는 date가 현재 날짜와 똑같으면 일정이라는 글자를 추가
                        if (holder.schedule.getVisibility() == View.INVISIBLE) {
                            Log.d("!!!!!!!!!!!!!!!!!!", "실행된다");
                            holder.schedule.setVisibility(View.VISIBLE);
                            toDo.add(componentArrayList.get(i));
                        } else {
                            toDo.add(componentArrayList.get(i));
                        }
                    }
                }
            } catch (Exception e) {

            }
        }
        try {
            if (position % 7 == 0)
                holder.day.setTextColor(Color.BLUE);
            if ((position + 1) % 7 == 0)
                holder.day.setTextColor(Color.RED);
            if (Integer.parseInt(list.get(position)) == checkCalendar.get(Calendar.DATE) && month == checkCalendar.get(Calendar.MONTH)+1 && year == checkCalendar.get(Calendar.YEAR))
                holder.day.setTextColor(Color.MAGENTA);
        }catch(NumberFormatException e){

        }
        if(!(position < check.get(Calendar.DAY_OF_WEEK) - 1) && !(position >= list.size() - (7 - check2.get(Calendar.DAY_OF_WEEK)))) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                //리사이클러뷰의 아이템을 클릭했을 때 작동하는 메서드
                //현재 더블탭 구현
                public void onClick(View v) {
                    if (System.currentTimeMillis() > checktime + 500) {
                        if (context.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
                            if (saveData == null) {
                                saveData = v;
                                v.setBackgroundResource(R.drawable.ic_baseline_selectedcalendarbackground);
                                ((ReminderActivity) context).setDateLand(Integer.toString(year) + '/' + Integer.toString(month) + '/' + holder.day.getText().toString());
                            } else if (saveData == v) {
                                v.setBackgroundResource(R.drawable.ic_baseline_calendarbackground);
                                saveData = null;
                                ((ReminderActivity)context).setDateLand("");
                            } else {
                                saveData.setBackgroundResource(R.drawable.ic_baseline_calendarbackground);
                                v.setBackgroundResource(R.drawable.ic_baseline_selectedcalendarbackground);
                                saveData = v;
                                ((ReminderActivity) context).setDateLand(Integer.toString(year) + '/' + Integer.toString(month) + '/' + holder.day.getText().toString());
                            }
                        } else {
                            checktime = System.currentTimeMillis();
                            if (saveData == null) {
                                saveData = v;
                                v.setBackgroundResource(R.drawable.ic_baseline_selectedcalendarbackground);
                                CalendarFragment.selectedDate = Integer.toString(year) + '/' + Integer.toString(month) + '/' + holder.day.getText().toString();
                            } else if (saveData == v) {
                                v.setBackgroundResource(R.drawable.ic_baseline_calendarbackground);
                                saveData = null;
                            } else {
                                saveData.setBackgroundResource(R.drawable.ic_baseline_calendarbackground);
                                v.setBackgroundResource(R.drawable.ic_baseline_selectedcalendarbackground);
                                saveData = v;
                                CalendarFragment.selectedDate = Integer.toString(year) + '/' + Integer.toString(month) + '/' + holder.day.getText().toString();
                            }
                        }
                        return;
                    }
                    if (System.currentTimeMillis() <= 500 + checktime && holder.schedule.getVisibility() == View.VISIBLE) {
                        Log.d("!!!!!!!!!!!!!!!!!", "더블 탭 성공!");
                        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                            saveData = v;
                            dialog = new CalendarDialog(context, toDo);
                            if (saveData.getParent() != null)
                                ((ViewGroup) saveData.getParent()).removeView(saveData);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(saveData);
                            dialog.show();
                            componentArrayList = CalreminderData.componentDataDao.getHasDateComponent();
                            notifyItemChanged(position);
                            Log.d("!!!!!!!!!!!!!!!!!", "다이얼로그 성공");
                        }
                    }
                    else if(System.currentTimeMillis() <= 500 + checktime && v != saveData){
                        saveData.setBackgroundResource(R.drawable.ic_baseline_calendarbackground);
                        saveData = v;
                        v.setBackgroundResource(R.drawable.ic_baseline_selectedcalendarbackground);

                    }
                }
            });
        }
        else{
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(position < check.get(Calendar.DAY_OF_WEEK) - 1)
                        CalendarFragment.leftPageMove();
                    else
                        CalendarFragment.rightPageMove();
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

}