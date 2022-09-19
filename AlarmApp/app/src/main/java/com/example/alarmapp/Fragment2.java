package com.example.alarmapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Fragment2 extends Fragment implements OnTabItemSelectedListener {
    Context context;
    OnTabItemSelectedListener listener;

    ImageView weatherIcon;
    TextView dateTextView;
    TextView locationTextView;
    EditText title;
    EditText receiver;
    DBHelper dbHelper;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);
        dbHelper = new DBHelper(getActivity());

        initUI(rootView);

        return rootView;
    }

    private void initUI(ViewGroup rootView) {

        Button saveButton = rootView.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    dbHelper.InsertAlarm("HI", "HI", "HI", 1, "HI", "HI");
                    listener.onTabSelected(0);
                }
            }
        });

        Button deleteButton = rootView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onTabSelected(0);
                }
            }
        });

        Button closeButton = rootView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onTabSelected(0);
                }
            }
        });

        title = rootView.findViewById(R.id.titleOfAlarm);
        receiver = rootView.findViewById(R.id.receiver);
        applyItem();

    }

    public void setItem(AlarmItem item) {
        this.item = item;
    }

    public void applyItem() {
        if(item !=null) {
            title.setText(item.getTitle());
            receiver.setText(item.getReceiver());
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
