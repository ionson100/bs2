package com.example.bs2;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.io.IOException;

public class SettingsActivity extends AppCompatActivity {

    Settings  settings;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        CheckBox checkBox1=findViewById(R.id.cb_flash);
        CheckBox checkBox2=findViewById(R.id.cb_focus);
        final EditText editText=findViewById(R.id.edit_aspect);

        try {
            settings = Settings.getSettings(SettingsActivity.this);
            checkBox1.setChecked(settings.isFlash);
            checkBox2.setChecked(settings.isFocus);
            editText.setText(""+settings.acpect);
        } catch (IOException e) {
            e.printStackTrace();
        }
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.isFlash=isChecked;
                try {
                    Settings.save(settings,SettingsActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                settings.isFocus=isChecked;
                try {
                    Settings.save(settings,SettingsActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    settings.acpect=Integer.parseInt(s.toString());
                }catch (Exception e){
                    editText.setText("0");
                }

                try {
                    Settings.save(settings,SettingsActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


    }
}
