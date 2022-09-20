package com.example.alarmapp;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Fragment1 extends Fragment {
    RecyclerView recyclerView;
    NoteAdapter adapter;
    Fragment2 fragment2;

    Context context;
    OnTabItemSelectedListener listener;

    public void onAttach(Context context){
        super.onAttach(context);

        this.context = context;

        if (context instanceof OnTabItemSelectedListener) {
            listener = (OnTabItemSelectedListener) context;
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();

        if(context != null){
            context = null;
            listener = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment1, container, false);

        initUI(rootView);

        return rootView;

    }


    private void initUI(ViewGroup rootView) {

        Button addButton = rootView.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onTabSelected(1);
                }
            }
        });

        recyclerView = rootView.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new NoteAdapter(context);

        adapter.addItem(new AlarmItem(0, "박나은", "09월 01일", "AM 09 : 00", 5000, "01083155904", "깨워줘"));
        adapter.addItem(new AlarmItem(0, "조수아", "09월 01일", "AM 09 : 00", 5000, "01083155904", "깨워줘"));
        adapter.addItem(new AlarmItem(0, "최문기", "09월 01일", "AM 09 : 00", 5000, "01083155904", "깨워줘"));

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnNoteItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(NoteAdapter.ViewHolder holder, View view, int position) {
                AlarmItem item = adapter.getItem(position);

                Toast.makeText(getContext(), "선택: " +item.getTitle(), Toast.LENGTH_SHORT).show();

                fragment2 = new Fragment2();
                fragment2.setItem(item);

//                MainActivity ad= (MainActivity) getActivity();
//                ad.bottomNavigation.setSelectedItemId(R.id.tab2);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment2).addToBackStack(null).commit();


            }
        });


    }

}
