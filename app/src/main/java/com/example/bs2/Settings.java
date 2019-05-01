package com.example.bs2;


import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


public class Settings {
    private Settings(){

    }
    public boolean isFlash=true;
    public boolean isFocus=true;
    public int acpect=0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Settings getSettings(Context context) throws IOException {

        Settings settings=new Settings();
        File file = new File(context.getFilesDir(),"set.txt");
        if(file.exists()==false){
            return settings;
        }else {
            FileInputStream fis = context.openFileInput("set.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
          String[] s=sb.toString().split("@");
          if(s[0].equals("true")){
              settings.isFlash=true;
          }else {
              settings.isFlash=false;
          }
          if(s[1].equals("true")){
              settings.isFocus=true;
          }else {
              settings.isFocus=false;
          }
          settings.acpect= Integer.parseInt(s[2]);
          return settings;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void save(Settings settings,Context context) throws IOException {

        File file = new File(context.getFilesDir(),"set.txt");
        if(file.exists()){
            file.delete();
        }
        StringBuilder builder=new StringBuilder();
        builder.append(settings.isFlash).append("@").append(settings.isFocus).append("@").append(settings.acpect);
        FileWriter writer = new FileWriter(file);
        writer.append(builder.toString());
        writer.flush();
        writer.close();
    }

}
