package com.example.wetrace;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ContactTracingActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    MessageListener mMessageListener;
    Message mMessage;
    long risk;
    ArrayAdapter<String> arrayAdapter;
    String riskStatus = "";
    Vibrator vib;
    Map<String, Object> contacts;
    FirebaseDatabase firebaseDatabase;
    private TextView riskCT;
    private ConstraintLayout riskCL;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_tracing);
        listView = findViewById(R.id.listView);
        String[] list = new String[]{"Item 1"};
        final List<String> listItems = new ArrayList<String>(Arrays.asList(list));
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        listView.setAdapter(arrayAdapter);
        getSupportActionBar().setTitle("Contact Tracing");
        riskCT = findViewById(R.id.riskCT);
        riskCL = findViewById(R.id.riskCL);
        arrayAdapter.clear();
        contacts = new HashMap<>();
        setRiskStatus();
    }

    private void setRiskStatus() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("Users").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                risk = (long) snapshot.child("Risk").getValue();
                if (risk == -1) {
                    riskCT.setText("Low Risk");
                    riskStatus = "L";
                    riskCT.setTextColor(getResources().getColor(R.color.green));
                    riskCL.setBackgroundColor(getResources().getColor(R.color.lightGreen));
                } else if (risk == 0) {
                    riskCT.setText("Medium Risk");
                    riskStatus = "M";
                    riskCT.setTextColor(getResources().getColor(R.color.yellow));
                    riskCL.setBackgroundColor(getResources().getColor(R.color.lightYellow));
                } else if (risk == 1) {
                    riskCT.setText("High Risk");
                    riskStatus = "H";
                    riskCT.setTextColor(getResources().getColor(R.color.red));
                    riskCL.setBackgroundColor(getResources().getColor(R.color.lightRed));
                }
                mMessageListener = new MessageListener() {
                    @Override
                    public void onFound(Message message) {
                        String riskReceived = new String(message.getContent());
                        if (riskReceived.substring(0, 1).equals("H")) {
                            arrayAdapter.add("Risk: High");
                            vib.vibrate(500);
                            Toast.makeText(ContactTracingActivity.this, "You came in contact with a COVID Risk Patient: High", Toast.LENGTH_SHORT).show();
                        }
                        if (riskReceived.substring(0, 1).equals("L")) {
                            contacts.put(UUID.randomUUID().toString(), riskReceived.substring(1));
                            Toast.makeText(ContactTracingActivity.this, "You came in contact with a COVID Risk Patient: Low", Toast.LENGTH_SHORT).show();
                            firebaseDatabase.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Contacts").updateChildren(contacts);
                        }
                        if (arrayAdapter.getCount() == 20) {
                            Toast.makeText(ContactTracingActivity.this, "Avoid contact with person nearby!\nYour Risk is now: Medium! ", Toast.LENGTH_LONG).show();
                            final Map<String, Object> riskData = new HashMap<>();
                            riskData.put("Risk", 0);
                            riskCT.setText("Medium Risk");
                            riskCT.setTextColor(getResources().getColor(R.color.yellow));
                            riskCL.setBackgroundColor(getResources().getColor(R.color.lightYellow));
                            firebaseDatabase.getReference().child("USERS").child(firebaseAuth.getUid()).updateChildren(riskData);
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onLost(Message message) {
                    }
                };
                String msg = riskStatus + FirebaseAuth.getInstance().getUid();
                mMessage = new Message(msg.getBytes());
                Nearby.getMessagesClient(ContactTracingActivity.this).publish(mMessage);
                Nearby.getMessagesClient(ContactTracingActivity.this).subscribe(mMessageListener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ContactTracingActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        setRiskStatus();
    }

    @Override
    public void onStop() {
        Nearby.getMessagesClient(this).unpublish(mMessage);
        Nearby.getMessagesClient(this).unsubscribe(mMessageListener);
        super.onStop();
    }
}