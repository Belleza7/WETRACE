package com.example.wetrace;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SelfAssessmentTestActivity extends AppCompatActivity {
    int gender, age, fever, cough, breath, throat, hoarseness, head, nose, lose, condition, contact, risk;
    FirebaseDatabase firebaseDatabase;
    private ConstraintLayout startCL, questionCL, resultCL, cl18;
    private RadioButton yes, no, male, female, yesF, noF, yesC, noC, yesB, noB, yesS, noS, yesH, noH, yesHead, noHead, yesN, noN, yesL, noL, yesContact, noContact;
    private CheckBox checkBox, checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6;
    private Button submitBtn, doneBtn;
    private TextView riskTV, recommendTV, safetyTips;
    private EditText ageED;
    private FirebaseAuth fba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_assessment_test);

        fba = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);
        yesF = findViewById(R.id.yesF);
        noF = findViewById(R.id.noF);
        yesC = findViewById(R.id.yesC);
        noC = findViewById(R.id.noC);
        yesB = findViewById(R.id.yesB);
        noB = findViewById(R.id.noB);
        yesS = findViewById(R.id.yesS);
        noS = findViewById(R.id.noS);
        yesH = findViewById(R.id.yesH);
        noH = findViewById(R.id.noH);
        yesHead = findViewById(R.id.yesHead);
        noHead = findViewById(R.id.noHead);
        yesN = findViewById(R.id.yesN);
        noN = findViewById(R.id.noN);
        yesL = findViewById(R.id.yesL);
        noL = findViewById(R.id.noL);
        yesContact = findViewById(R.id.yesContact);
        noContact = findViewById(R.id.noContact);

        riskTV = findViewById(R.id.riskTV);
        recommendTV = findViewById(R.id.recommendTV);

        checkBox = findViewById(R.id.checkBox);
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        checkBox5 = findViewById(R.id.checkBox5);
        checkBox6 = findViewById(R.id.checkBox6);

        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        ageED = findViewById(R.id.ageET);
        final ScrollView sv = findViewById(R.id.sv);

        startCL = findViewById(R.id.startCL);
        resultCL = findViewById(R.id.resultCL);
        submitBtn = findViewById(R.id.submitBtn);
        questionCL = findViewById(R.id.questionCL);
        safetyTips = findViewById(R.id.safetyTips);
        cl18 = findViewById(R.id.constraintLayout18);

        doneBtn = findViewById(R.id.doneBtn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Map<String, Object> riskData = new HashMap<>();
                riskData.put("Risk", risk);
                firebaseDatabase.getReference().child("Users").child(fba.getUid()).updateChildren(riskData);
                Intent i = new Intent(SelfAssessmentTestActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCL.setVisibility(View.GONE);
                resultCL.setVisibility(View.VISIBLE);
                risk = 1;
                questionCL.setVisibility(View.GONE);
                resultCL.setVisibility(View.VISIBLE);
                riskTV.setText("High Risk");
                riskTV.setTextColor(getResources().getColor(R.color.red));
                cl18.setBackgroundColor(getResources().getColor(R.color.lightRed));
                safetyTips.setText("Wash your hands and don't touch your face.\nStay atleast 6 feet away from people around you.\nAlways wear a mask.\nDon't share food, and any other items, and avoid shared surfaces.\nOpen windows for better ventilation.");
                recommendTV.setText("Home Quarantine for 14 days. Avoid Public Activities, Monitor your health daily for any active symptoms. Avoid travel unless necessary.");
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCL.setVisibility(View.GONE);
                questionCL.setVisibility(View.VISIBLE);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (male.isChecked() || female.isChecked()) {
                    if (!TextUtils.isEmpty(ageED.getText().toString())) {
                        if (yesF.isChecked() || noF.isChecked()) {
                            if (yesC.isChecked() || noC.isChecked()) {
                                if (yesB.isChecked() || noB.isChecked()) {
                                    if (yesS.isChecked() || noS.isChecked()) {
                                        if (yesH.isChecked() || noH.isChecked()) {
                                            if (yesHead.isChecked() || noHead.isChecked()) {
                                                if (yesN.isChecked() || noN.isChecked()) {
                                                    if (yesL.isChecked() || noL.isChecked()) {
                                                        if (checkBox.isChecked() || checkBox1.isChecked() || checkBox2.isChecked() || checkBox3.isChecked() || checkBox4.isChecked() || checkBox5.isChecked() || checkBox6.isChecked()) {
                                                            if (yesContact.isChecked() || noContact.isChecked()) {
                                                                setVariables();
                                                            } else {
                                                                sv.scrollTo(0, (int) yesContact.getY());
                                                                Toast.makeText(getApplicationContext(), "Select any option!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        } else {
                                                            sv.scrollTo(0, (int) checkBox.getY());
                                                            Toast.makeText(getApplicationContext(), "Select any option!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        sv.scrollTo(0, (int) yesL.getY());
                                                        Toast.makeText(getApplicationContext(), "Select any option!", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    sv.scrollTo(0, (int) yesN.getY());
                                                    Toast.makeText(getApplicationContext(), "Select any option!", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                sv.scrollTo(0, (int) yesHead.getY());
                                                Toast.makeText(getApplicationContext(), "Select any option!", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            sv.scrollTo(0, (int) yesH.getY());
                                            Toast.makeText(getApplicationContext(), "Select any option!", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        sv.scrollTo(0, (int) yesS.getY());
                                        Toast.makeText(getApplicationContext(), "Select any option!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    sv.scrollTo(0, (int) noB.getY());
                                    Toast.makeText(getApplicationContext(), "Select any option!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                sv.scrollTo(0, (int) yesC.getY());
                                Toast.makeText(getApplicationContext(), "Select any option!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            sv.scrollTo(0, (int) yesF.getY());
                            Toast.makeText(getApplicationContext(), "Select any option!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        ageED.requestFocus();
                        ageED.setError("Enter your age!");
                    }
                } else {
                    sv.scrollTo(0, (int) male.getY());
                    Toast.makeText(getApplicationContext(), "Select your gender!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setVariables() {
        gender = (male.isChecked()) ? 1 : 0;
        age = (Integer.parseInt(ageED.getText().toString()) > 60) ? 1 : 0;
        fever = (yesF.isChecked()) ? 1 : 0;
        cough = (yesC.isChecked()) ? 1 : 0;
        breath = (yesB.isChecked()) ? 1 : 0;
        throat = (yesS.isChecked()) ? 1 : 0;
        hoarseness = (yesH.isChecked()) ? 1 : 0;
        head = (yesHead.isChecked()) ? 1 : 0;
        nose = (yesN.isChecked()) ? 1 : 0;
        lose = (yesL.isChecked()) ? 1 : 0;
        condition = (checkBox.isChecked()) ? 0 : 1;
        contact = (yesContact.isChecked()) ? 1 : 0;

        if (((age == 1) && (fever == 1) && (cough == 1) && (breath == 1) && (throat == 1) && (hoarseness == 1) && (head == 1) && (nose == 1) && (lose == 1) || (contact == 1))) {
            risk = 1;
            questionCL.setVisibility(View.GONE);
            resultCL.setVisibility(View.VISIBLE);
            riskTV.setText("High Risk");
            riskTV.setTextColor(getResources().getColor(R.color.red));
            cl18.setBackgroundColor(getResources().getColor(R.color.lightRed));
            safetyTips.setText("Wash your hands and don't touch your face.\nStay atleast 6 feet away from people around you.\nAlways wear a mask.\nDon't share food, and any other items, and avoid shared surfaces.\nOpen windows for better ventilation.");
            recommendTV.setText("Home Quarantine for 14 days. Avoid Public Activities, Monitor your health daily for any active symptoms. Avoid travel unless necessary.");
        } else if (((age == 1) && (fever == 1) && (cough == 1) && (head == 1) && (nose == 1) && (lose == 1))) {
            risk = 0;
            questionCL.setVisibility(View.GONE);
            resultCL.setVisibility(View.VISIBLE);
            riskTV.setText("Medium Risk");
            riskTV.setTextColor(getResources().getColor(R.color.yellow));
            cl18.setBackgroundColor(getResources().getColor(R.color.lightYellow));
            safetyTips.setText("Wash your hands and don't touch your face.\nStay atleast 6 feet away from people around you.\nWear a mask.\nDon't share food, and any other items, and avoid shared surfaces.");
            recommendTV.setText("Home Quarantine for 14 days. Avoid Public Activities, Monitor your health daily for any active symptoms.");
        } else {
            risk = -1;
            questionCL.setVisibility(View.GONE);
            resultCL.setVisibility(View.VISIBLE);
            riskTV.setText("Low Risk");
            riskTV.setTextColor(getResources().getColor(R.color.green));
            cl18.setBackgroundColor(getResources().getColor(R.color.lightGreen));
            safetyTips.setText("Stay home as much as possible.\ntry to allow only people you live with into your home.\nWash your hands frequently.\nIf you are sick, stay home and isolate from housemates.");
            recommendTV.setText("Practice personal hygiene as a preventive measure. Avoid public activities outside home.");
        }
    }
}