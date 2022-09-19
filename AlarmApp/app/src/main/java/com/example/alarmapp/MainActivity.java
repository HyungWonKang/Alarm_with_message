package com.example.alarmapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements OnTabItemSelectedListener {
    private static final String TAG = "MainActivity";

    Fragment1 fragment1;
    Fragment2 fragment2;

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();


        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab1:
                        Toast.makeText(getApplicationContext(), "첫 번째 탭 선택됨", Toast.LENGTH_LONG).show();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment1).commit();

                        return true;
                    case R.id.tab2:
                        Toast.makeText(getApplicationContext(), "두 번째 탭 선택됨", Toast.LENGTH_LONG).show();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment2).commit();

                        return true;

                }

                return false;
            }
        });

    }

    public void onTabSelected(int position) {
        if (position == 0) {
            bottomNavigation.setSelectedItemId(R.id.tab1);
        } else if (position == 1) {
            bottomNavigation.setSelectedItemId(R.id.tab2);
        }
    }

    long time =0;
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - time >= 1000) {
            time = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "뒤로 가기 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() - time < 1000) { // 뒤로 가기 한번 더 눌렀을때의 시간간격 텀이 1초
            finishAffinity();
            System.runFinalization();
            System.exit(0);
        }
    }

}

