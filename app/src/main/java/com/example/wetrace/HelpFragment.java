package com.example.wetrace;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

public class HelpFragment extends Fragment {
    ConstraintLayout call1075, testCL;
    TextView callMah, callCentre, callMum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_help, container, false);

        call1075 = v.findViewById(R.id.call1075);
        call1075.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("tel:1075"));
                startActivity(intent);
            }
        });

        testCL = v.findViewById(R.id.testCentreCL);
        testCL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TestCentreActivity.class);
                startActivity(i);
            }
        });

        callMah = v.findViewById(R.id.callMah);
        callMah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("tel:022-22027990"));
                startActivity(intent);
            }
        });

        callCentre = v.findViewById(R.id.callCentre);
        callCentre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:+91-11-23978046"));
                startActivity(intent);
            }
        });

        callMum = v.findViewById(R.id.callMum);
        callMum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:022-22664232"));
                startActivity(intent);
            }
        });
        return v;
    }
}