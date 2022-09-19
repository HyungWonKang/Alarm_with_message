package com.example.alarmapp;

import android.view.View;

public interface OnNoteItemClickListener {

    public void onItemClick(NoteAdapter.ViewHolder holder, View view, int position);

}