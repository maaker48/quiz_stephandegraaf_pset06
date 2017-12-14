package com.example.stephan.quiz;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HighscoreFragment extends DialogFragment {
    public HighscoreFragment() {
        // Required empty public constructor
    }
    public List<String> highscores = new ArrayList<>();
    public ArrayAdapter adapter;
    public ListView lv;
    public Query fb;
    public ValueEventListener listener;

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        lv = getView().findViewById(R.id.lv_highscores);
        refresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_highscore, container, false);
        // Inflate the layout for this fragment
        return view;
    }

    public void refresh(){
        fb = FirebaseDatabase.getInstance().getReference().orderByChild("score");
        listener = fb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                highscores = new ArrayList<String>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.getKey();
                    int score = ds.child("score").getValue(Integer.class);
                    String hsRow = name + " : " + Integer.toString(score);
                    highscores.add(hsRow);
                }
                updateAdaptor();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateAdaptor () {
        Collections.reverse(highscores);
        adapter = new ArrayAdapter(getContext(), R.layout.row_highscore, R.id.tv_highscore, highscores);
        lv.setAdapter(adapter);

        //Destroy listener, else it tries to inflate views which are not in the lifecycle.
        fb.removeEventListener(listener);
    }

}