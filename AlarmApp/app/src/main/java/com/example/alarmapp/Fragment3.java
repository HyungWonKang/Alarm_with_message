package com.example.alarmapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Fragment3 extends Fragment {

    MediaPlayer mediaPlayer;
    boolean flag = true;
    private CountDownTimer countDownTimer;
    private  long time = 0;
    private  long tempTime = 0;
    private TextView duration;
    private Button stopButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_3, container, false);

        final TextView currentTime = (TextView) rootView.findViewById(R.id.currentTime);
        duration = (TextView)rootView.findViewById(R.id.duration);
        stopButton = (Button) rootView.findViewById(R.id.stopBtn);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                flag=false;
                countDownTimer.cancel();
            }
        });

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

        mediaPlayer = MediaPlayer.create(getContext(), R.raw.alarmmusic);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        startTimer();

        // 현재 시간 setting
        (new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag == true)
                    try {
                        Thread.sleep(1000);
                        getActivity().runOnUiThread(new Runnable() // start actions in UI thread
                        {
                            @Override
                            public void run() {
                                currentTime.setText(getCurrentTime());
                            }
                        });
                    } catch (InterruptedException e) {
                        // ooops
                    }
            }
        })).start();

        return rootView;
    }

    public String getCurrentTime() {
        long currentTime = System.currentTimeMillis();
        Date date = new Date(currentTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh : mm : ss");
        String getTime = dateFormat.format(date);
        return getTime;
    }

    // 10초 후에 작동
    public void startTimer() {
        tempTime = 10000;
        time = tempTime;

        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tempTime = millisUntilFinished;
                updateDuration();
            }
            @Override
            public void onFinish() {
                timeOver();
            }
        }.start();
    }

    // 남은 시간 보여주기
    public void updateDuration() {

        int min = (int)tempTime/60000;
        int sec = (int)tempTime%60000/1000;

        String timeLeftText = "";
        if(min<10)
            timeLeftText +="0";
        timeLeftText += min + " : ";

        if (sec<10)
            timeLeftText += "0";
        timeLeftText += sec;

        duration.setText(timeLeftText);
    }

    // 메시지 보내기
    public void timeOver() {
        mediaPlayer.stop();
        flag=false;
        String phoneNo = "01071377428";
        String sms = "날 깨워조! (알람앱 Test)";
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, sms, null, null);
            Toast.makeText(getContext(), "전송완료!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), "SMS faild, please try again later!", Toast.LENGTH_LONG).show();
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


}