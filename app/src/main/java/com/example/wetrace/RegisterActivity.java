package com.example.wetrace;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    private EditText name;
    private EditText email;
    private EditText pass;
    private EditText phone;
    private Button regBtn;
    private TextView alreadyHave;
    private FirebaseAuth fba;
    private FirebaseFirestore ffs;
    private ProgressBar pb;
    private String regex_email = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.regName);
        email = findViewById(R.id.regEmail);
        pass = findViewById(R.id.regPassword);
        phone = findViewById(R.id.regPhone);
        regBtn = findViewById(R.id.regBtn);
        alreadyHave = findViewById(R.id.alreadyHave);
        pb = findViewById(R.id.regPB);
        fba = FirebaseAuth.getInstance();
        ffs = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        alreadyHave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().matches(regex_email)) {
                    regBtn.setVisibility(View.GONE);
                    pb.setVisibility(View.VISIBLE);
                    checkEmailAndPass();
                } else {
                    email.requestFocus();
                    email.setError("Enter valid Email Address!");
                }
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void checkInputs() {
        if (!TextUtils.isEmpty(email.getText())) {
            if (!TextUtils.isEmpty(name.getText())) {
                if (!TextUtils.isEmpty(pass.getText()) && pass.length() >= 8) {
                    if (!TextUtils.isEmpty(phone.getText()) && phone.length() > 9) {
                        regBtn.setEnabled(true);
                    } else {
                        regBtn.setEnabled(false);
                    }
                } else {
                    regBtn.setEnabled(false);
                }
            } else {
                regBtn.setEnabled(false);
            }
        } else {
            regBtn.setEnabled(false);
        }
    }

    private void checkEmailAndPass() {
        fba.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final Map<String, Object> userdata = new HashMap<>();
                    userdata.put("FullName", name.getText().toString());
                    userdata.put("Email", email.getText().toString());
                    userdata.put("Password", pass.getText().toString());
                    userdata.put("Phone", phone.getText().toString());
                    userdata.put("Risk", "");

                    firebaseDatabase.getReference().child("Users").child(fba.getUid()).setValue(userdata)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(i);
                                    } else {
                                        pb.setVisibility(View.INVISIBLE);
                                        String error = task.getException().getMessage();
                                        regBtn.setVisibility(View.VISIBLE);
                                        Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    pb.setVisibility(View.INVISIBLE);
                    String error = task.getException().getMessage();
                    regBtn.setVisibility(View.VISIBLE);
                    Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}