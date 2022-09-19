package com.example.alarmapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "AlarmApp.db";

    public DBHelper(@Nullable Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //데이터 베이스가 생성이 될 때 호출
        db.execSQL("CREATE TABLE IF NOT EXISTS Alarm (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, date TEXT NOT NULL, time TEXT NOT NULL, duration INTEGER NOT NULL, receiver TEXT NOT NULL, message TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onCreate(db);
    }

    //SELECT문-조회
    public ArrayList<AlarmItem> getAlarm(){
        ArrayList<AlarmItem> alarmItems = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Alarm ORDER BY id DESC", null);
        if(cursor.getCount() !=0){
            // 조회 데이터가 있을 떄 수행
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow("duration"));
                String receiver = cursor.getString(cursor.getColumnIndexOrThrow("receiver"));
                String message = cursor.getString(cursor.getColumnIndexOrThrow("message"));

                AlarmItem alarmItem = new AlarmItem(id, title, date, time, duration, receiver, message);
                alarmItem.setId(id);
                alarmItem.setTitle(title);
                alarmItem.setDate(date);
                alarmItem.setTime(time);
                alarmItem.setDuration(duration);
                alarmItem.setReceiver(receiver);
                alarmItem.setMessage(message);
                alarmItems.add(alarmItem);
            }
        }
        cursor.close();

        return alarmItems;
    }

    //INSERT문-추가
    public void InsertAlarm(String _title, String _date, String _time, int _duration, String _receiver, String _message){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Alarm (title, date, time, duration, receiver, message) VALUES('" + _title +"','" + _date +"','"+ _time +"','"+ _duration +"','"+ _receiver +"','"+ _message + "');");
    }

    //UPDATE문-수정
    public void UpdateAlarm(String _title, String _date, String _time, int _duration, String _receiver, String _message, int _id){
        SQLiteDatabase db = getWritableDatabase();
        //언더바 없는 변수는 원래 있던 데이터, 언더바 있는 변수는 새로 업데이트 할 데이터
        db.execSQL("UPDATE Alram SET title='" + _title + "', date='" + _date + "', time='" + _time + "', duratione='" + _duration + "', receiver='" + _receiver + "', message='" + _message  + "' WHERE id='" + _id + "'");
    }

    //DELETE문-삭제
    public void DeleteAlarm(int _id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Alram WHERE id = '" + _id + "'");
    }
}
