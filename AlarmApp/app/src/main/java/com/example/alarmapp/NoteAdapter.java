package com.example.alarmapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> implements OnNoteItemClickListener {
    ArrayList<AlarmItem> items = new ArrayList<AlarmItem>();
    DBHelper dbHelper;
    OnNoteItemClickListener listener;
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.note_item, viewGroup,false);


        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        AlarmItem item = items.get(position);
        viewHolder.setItem(item);
    }

    public void setOnItemClickListener(OnNoteItemClickListener listener) {
        this.listener = listener;
    }

    public NoteAdapter(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
        items = dbHelper.getAlarm();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(AlarmItem item) {
        items.add(item);
    }

    public void setItems(ArrayList<AlarmItem> items) {
        this.items = items;
    }

    public AlarmItem getItem(int position) {
        return items.get(position);
    }

    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout1;
        TextView contentsTextView;
        TextView dateTextView;
        TextView textView;
        TextView editTextTime;

        public ViewHolder(@NonNull View itemView, final OnNoteItemClickListener listener) {
            super(itemView);

            layout1 = itemView.findViewById(R.id.layout1);

            contentsTextView = itemView.findViewById(R.id.contentsTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            textView = itemView.findViewById(R.id.textView);
            editTextTime = itemView.findViewById(R.id.editTextTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null) {

                        listener.onItemClick(ViewHolder.this, v, position);
                    }
                }
            });
        }

        public void setItem(AlarmItem item) {
            contentsTextView.setText(item.getReceiver());
            dateTextView.setText(item.getDate());
            textView.setText(item.getTitle());
            editTextTime.setText(item.getTime());
        }
    }
}


