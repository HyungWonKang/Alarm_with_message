package com.example.alarmapp;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Fragment2 extends Fragment implements OnTabItemSelectedListener {
    Context context;
    OnTabItemSelectedListener listener;

    ImageView weatherIcon;
    TextView dateTextView;
    TextView locationTextView;
    EditText title;
    EditText receiver;
    DBHelper dbHelper;
    private ArrayList<AlarmItem> mAlarmItems;
    public int duration;
    DatePicker datePicker;
    TimePicker timePicker;

    EditText contentsInput;
    ImageView pictureImageView;

    AlarmItem item;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;

        if (context instanceof OnTabItemSelectedListener) {
            listener = (OnTabItemSelectedListener) context;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (context != null) {
            context = null;
            listener = null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);
        dbHelper = new DBHelper(getActivity());

        initUI(rootView);

        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initUI(ViewGroup rootView) {

        mAlarmItems = new ArrayList<>();
        dbHelper = new DBHelper(getContext());

        Button saveButton = rootView.findViewById(R.id.saveButton);
        Button deleteButton = rootView.findViewById(R.id.deleteButton);
        Button closeButton = rootView.findViewById(R.id.closeButton);
        title = rootView.findViewById(R.id.titleOfAlarm);  //제목
        RadioGroup radioGroup = rootView.findViewById(R.id.radioGroup);
        receiver = rootView.findViewById(R.id.receiver); // 전화번호
        contentsInput = rootView.findViewById(R.id.editTextTextPersonName2); //보낼 문자 내용

        //날짜
        datePicker = (DatePicker) rootView.findViewById(R.id.datePicker);
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int dayOfMonth = datePicker.getDayOfMonth();
        String date = String.format("%d-%d-%d", year, month, dayOfMonth);

        //시간
        timePicker = (TimePicker) rootView.findViewById(R.id.timePicker);
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        String time = String.format("%d-%d", hour, minute);

        //지속시간
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton2:
                        duration = 15000;
                        break;
                    case R.id.radioButton3:
                        duration = 30000;
                        break;
                    case R.id.radioButton4:
                        duration = 60000;
                        break;
                    case R.id.radioButton5:
                        duration = 120000;
                        break;
                };
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {

                    dbHelper.DeleteAlarm(item.getId());
                    Toast.makeText(context, "알람이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                    listener.onTabSelected(0);
                }
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onTabSelected(0);
                }
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Insert Database
                String titleString = title.getText().toString();

                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int dayOfMonth = datePicker.getDayOfMonth();
                String date = String.format("%d-%d-%d", year, month, dayOfMonth);

                TimePicker timePicker = (TimePicker) rootView.findViewById(R.id.timePicker);
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                String time = String.format("%d-%d", hour, minute);

                String receiverString = receiver.getText().toString();
                String contentString = contentsInput.getText().toString();

                dbHelper.InsertAlarm
                        (titleString, date, time, duration, receiverString, contentString);
                listener.onTabSelected(0);
            }

        });

        applyItem();

    }

    public void setItem(AlarmItem item) {
        this.item = item;
    }

    public void applyItem() {
        if(item !=null) {

            title.setText(item.getTitle());
            receiver.setText(item.getReceiver());
            contentsInput.setText(item.getMessage());

        }
    }

    @Override
    public void onTabSelected(int position) {
        if (position == 0) {
            MainActivity activity = (MainActivity) getActivity();
            activity.bottomNavigation.setSelectedItemId(R.id.tab1);
        } else if (position == 1) {
            MainActivity activity = (MainActivity) getActivity();
            activity.bottomNavigation.setSelectedItemId(R.id.tab2);
        }
    }
}
