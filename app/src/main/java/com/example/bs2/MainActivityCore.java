package com.example.bs2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityCore extends AppCompatActivity {

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_core);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView=findViewById(R.id.text_result);
        findViewById(R.id.button_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivityCore.this,MainActivity.class);
                MainActivityCore.this.startActivityForResult(intent,1158);
            }
        });
        toolbar.getMenu().add("Настройки");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_info_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent=new Intent(MainActivityCore.this,SettingsActivity.class);
        MainActivityCore.this.startActivity(intent);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        StringBuilder builder=new StringBuilder();
        if(data==null){
            builder.append(resultCode).append(System.lineSeparator());
            builder.append("null");

        }else {
            String type=data.getStringExtra("type");
            String datas=data.getStringExtra("data");

            builder.append("type code: ").append(type).append(System.lineSeparator());
            builder.append("data code: ").append(datas).append(System.lineSeparator());
            if(type.equals("DATA_MATRIX")&&datas.length()==29){
                builder.append("tobaco: ").append(datas.substring(0,14)).append("  ");
                builder.append(datas.substring(15,22)).append("  ");
                builder.append(datas.substring(23,28));
            }

        }
        textView.setText(builder.toString());
    }
}
