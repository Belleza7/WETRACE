package com.example.wetrace;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TaskFragment extends Fragment {
    ConstraintLayout selfTestCL, trackMeCL, contactTracingCL;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        selfTestCL = v.findViewById(R.id.selfTestCL);
        selfTestCL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SelfAssessmentTestActivity.class);
                startActivity(i);
            }
        });

        trackMeCL = v.findViewById(R.id.trackMeCL);
        trackMeCL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TrackMeActivity.class);
                startActivity(i);
            }
        });

        contactTracingCL = v.findViewById(R.id.contactTracingCL);
        contactTracingCL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseDatabase.getReference().child("Users").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child("Risk").getValue().toString().equals("")) {
                            Toast.makeText(getContext(), "Please, Complete the Self Assessment Test!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getActivity(), SelfAssessmentTestActivity.class);
                            startActivity(i);
                        } else {
                            Intent i = new Intent(getActivity(), ContactTracingActivity.class);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
        return v;
    }
}