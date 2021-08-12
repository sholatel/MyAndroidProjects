package com.example.frequencydisplay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
EditText minimumAllowableFrequencyEdit,maximumAllowableFrequencyEdit;
CheckBox alarmCheckbox;
RadioButton soundRadio,vibrationRadio;
Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        minimumAllowableFrequencyEdit=findViewById(R.id.minimumAllowableFrequencyEdit);
        maximumAllowableFrequencyEdit=findViewById(R.id.maximumAllowableFrequencyEdit);
        alarmCheckbox=findViewById(R.id.alarmCheckbox);
        soundRadio=findViewById(R.id.soundRadio);
        vibrationRadio=findViewById(R.id.vibrationRadio);
        saveButton=findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Toast.makeText(SettingsActivity.this, "Settings updated", Toast.LENGTH_SHORT).show();
            }
        });

        //load previously saved data
        loadData();

    }

    public void loadData(){
        GridDataClass dataClass = new GridDataClass();
        minimumAllowableFrequencyEdit.setText(dataClass.getData(this,"minimumAllowableFrequency"));
        maximumAllowableFrequencyEdit.setText(dataClass.getData(this,"maximumAllowableFrequencyEdit"));

        if(dataClass.getData(this,"alarmCheckbox").contains("true")){
            alarmCheckbox.setChecked(true);
        }
        else {
            alarmCheckbox.setChecked(false);
        }

        if(dataClass.getData(this,"alarmType").contains("sound")){
            soundRadio.setChecked(true);
        }
        else {
            vibrationRadio.setChecked(true);
        }

    }

    public void saveData(){
        GridDataClass dataClass = new GridDataClass();
        dataClass.saveData(this,"minimumAllowableFrequency",minimumAllowableFrequencyEdit.getText().toString());
        dataClass.saveData(this,"maximumAllowableFrequencyEdit",maximumAllowableFrequencyEdit.getText().toString());


        if(alarmCheckbox.isChecked()){
            dataClass.saveData(this,"alarmCheckbox","true");
        }
        else {
            dataClass.saveData(this,"alarmCheckbox","false");
        }

        if(soundRadio.isChecked()){
            dataClass.saveData(this,"alarmType","sound");
        }
        else {
            dataClass.saveData(this,"alarmType","vibration");
        }


    }
}