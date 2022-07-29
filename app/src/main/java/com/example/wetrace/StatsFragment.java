package com.example.wetrace;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StatsFragment extends Fragment {
    public String channelID = "channel1";
    FirebaseDatabase firebaseDatabase;
    ConstraintLayout riskCL;
    TextView riskTV;
    long risk;
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    Vibrator vib;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stats, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        String[] list = new String[]{"Item 1"};
        final List<String> listItems = new ArrayList<String>(Arrays.asList(list));
        arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.listview_custom_layout, listItems);
        riskCL = v.findViewById(R.id.riskStatsCL);
        riskTV = v.findViewById(R.id.riskStatsCT);
        listView = v.findViewById(R.id.riskLV);
        createNotificationChannel();
        listView.setDividerHeight(12);
        listView.setPadding(0, 8, 0, 8);
        arrayAdapter.clear();
        vib = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        listView.setAdapter(arrayAdapter);

        firebaseDatabase.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        risk = (long) snapshot.child("Risk").getValue();
                        if (risk == -1) {
                            riskTV.setText("Low Risk");
                            riskTV.setTextColor(getResources().getColor(R.color.green));
                            riskCL.setBackgroundColor(getResources().getColor(R.color.lightGreen));
                        } else if (risk == 0) {
                            riskTV.setText("Medium Risk");
                            riskTV.setTextColor(getResources().getColor(R.color.yellow));
                            riskCL.setBackgroundColor(getResources().getColor(R.color.lightYellow));
                        } else if (risk == 1) {
                            riskTV.setText("High Risk");
                            riskTV.setTextColor(getResources().getColor(R.color.red));
                            riskCL.setBackgroundColor(getResources().getColor(R.color.lightRed));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

        firebaseDatabase.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Contacts")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        arrayAdapter.clear();
                        Set<String> set = new HashSet<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            set.add(dataSnapshot.getValue().toString());
                        }
                        for (String value : set) {
                            firebaseDatabase.getReference().child("Users").child(value)
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot1) {
                                            long riskUser = (long) snapshot1.child("Risk").getValue();
                                            if (riskUser == 1) {
                                                vib.vibrate(500);
                                                arrayAdapter.add("High Risk");
                                                NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                                                        .setChannelId(channelID)
                                                        .setDefaults(Notification.DEFAULT_ALL)
                                                        .setContentTitle("Warning!")
                                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                                        .setAutoCancel(true)
                                                        .setSmallIcon(R.drawable.ic_baseline_warning_24)
                                                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Your Risk Status is updated to High\nPlease Avoid close contact with others!"))
                                                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
                                                NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                                                mNotificationManager.notify(1, builder.build());
                                                firebaseDatabase.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Risk").setValue(Integer.parseInt("1"));
                                            } else if (riskUser == 0) {
                                                arrayAdapter.add("Medium Risk");
                                            } else if (riskUser == -1) {
                                                arrayAdapter.add("Low Risk");
                                            }
                                            arrayAdapter.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
        return v;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelID, "Channel", importance);
            channel.setDescription("This is channel");
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}