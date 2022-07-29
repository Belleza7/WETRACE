package com.example.wetrace;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout logout = findViewById(R.id.logout);
        final LinearLayout home = findViewById(R.id.home);
        final ImageView homeIcon = findViewById(R.id.homeIcon);
        final TextView homeTxt = findViewById(R.id.homeTxt);
        final LinearLayout task = findViewById(R.id.task);
        final ImageView taskIcon = findViewById(R.id.taskIcon);
        final TextView taskTxt = findViewById(R.id.taskTxt);
        final LinearLayout help = findViewById(R.id.help);
        final ImageView helpIcon = findViewById(R.id.helpIcon);
        final TextView helpTxt = findViewById(R.id.helpTxt);
        final LinearLayout stats = findViewById(R.id.stats);
        final ImageView historyIcon = findViewById(R.id.historyIcon);
        final TextView historyTxt = findViewById(R.id.historyTxt);
        final FrameLayout fl = findViewById(R.id.mainFL);
        firebaseDatabase = FirebaseDatabase.getInstance();
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);

        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("WeTrace");
        builder.setMessage("Are you sure you want to Logout?");
        builder.setCancelable(true);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(fl.getId(), new HomeFragment());
        ft.commit();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                homeTxt.setTextColor(getResources().getColor(R.color.colorAccent));
                homeIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));

                task.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                taskTxt.setTextColor(getResources().getColor(R.color.colorPrimary));
                taskIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));

                help.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                helpTxt.setTextColor(getResources().getColor(R.color.colorPrimary));
                helpIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));

                stats.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                historyTxt.setTextColor(getResources().getColor(R.color.colorPrimary));
                historyIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(fl.getId(), new HomeFragment());
                ft.commit();
            }
        });

        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                homeTxt.setTextColor(getResources().getColor(R.color.colorPrimary));
                homeIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));

                task.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                taskTxt.setTextColor(getResources().getColor(R.color.colorAccent));
                taskIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));

                help.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                helpTxt.setTextColor(getResources().getColor(R.color.colorPrimary));
                helpIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));

                stats.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                historyTxt.setTextColor(getResources().getColor(R.color.colorPrimary));
                historyIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(fl.getId(), new TaskFragment());
                ft.commit();
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                homeTxt.setTextColor(getResources().getColor(R.color.colorPrimary));
                homeIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));

                task.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                taskTxt.setTextColor(getResources().getColor(R.color.colorPrimary));
                taskIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));

                help.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                helpTxt.setTextColor(getResources().getColor(R.color.colorAccent));
                helpIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));

                stats.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                historyTxt.setTextColor(getResources().getColor(R.color.colorPrimary));
                historyIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(fl.getId(), new HelpFragment());
                ft.commit();
            }
        });

        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                homeTxt.setTextColor(getResources().getColor(R.color.colorPrimary));
                homeIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));

                task.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                taskTxt.setTextColor(getResources().getColor(R.color.colorPrimary));
                taskIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));

                help.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                helpTxt.setTextColor(getResources().getColor(R.color.colorPrimary));
                helpIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));

                stats.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                historyTxt.setTextColor(getResources().getColor(R.color.colorAccent));
                historyIcon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));

                progressDialog.setMessage("Loading, Please Wait!");
                progressDialog.show();

                firebaseDatabase.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.child("Risk").getValue().equals("")) {
                                    progressDialog.dismiss();
                                    Toast.makeText(MainActivity.this, "Please, Complete Self Assessment Test under Task Tab!", Toast.LENGTH_SHORT).show();
                                } else {
                                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                    ft.replace(fl.getId(), new StatsFragment());
                                    progressDialog.dismiss();
                                    ft.commit();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
                Dialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.border_back));
                dialog.show();
            }
        });
    }
}