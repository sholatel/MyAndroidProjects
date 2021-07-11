package com.example.frequencydisplay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
EditText minimumAllowableFrequencyEdit,maximumAllowableFrequencyEdit;
Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        minimumAllowableFrequencyEdit=findViewById(R.id.minimumAllowableFrequencyEdit);
        maximumAllowableFrequencyEdit=findViewById(R.id.maximumAllowableFrequencyEdit);
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
    }

    public void saveData(){
        GridDataClass dataClass = new GridDataClass();
        dataClass.saveData(this,"minimumAllowableFrequency",minimumAllowableFrequencyEdit.getText().toString());
        dataClass.saveData(this,"maximumAllowableFrequencyEdit",maximumAllowableFrequencyEdit.getText().toString());
    }
}