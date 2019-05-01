package com.example.bs2;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        try {
            Settings settings=Settings.getSettings(MainActivity.this);
            mScannerView.setAutoFocus(settings.isFocus);
            mScannerView.setAspectTolerance ( (float)settings.acpect/10f );
            List<BarcodeFormat> list=new ArrayList<BarcodeFormat>(){{
                add(BarcodeFormat.UPC_A);
                add(BarcodeFormat.UPC_E);
                add(BarcodeFormat.EAN_13);
                add(BarcodeFormat.EAN_8);
                add(BarcodeFormat.RSS_14);
                add(BarcodeFormat.CODE_39);
                add(BarcodeFormat.CODE_93);
                add(BarcodeFormat.CODE_128);
                add(BarcodeFormat.ITF);
                add(BarcodeFormat.CODABAR);
                add(BarcodeFormat.QR_CODE);
                add(BarcodeFormat.DATA_MATRIX);
                add(BarcodeFormat.PDF_417);
            }};

            mScannerView.setFormats(list);
            mScannerView.setFlash(settings.isFlash);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        Toast.makeText(MainActivity.this,rawResult.getText(),Toast.LENGTH_LONG);
        // Do something with the result here
//        Log.v(TAG, rawResult.getText()); // Prints scan results
//        Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
        //mScannerView.stopCamera();
        Intent intent=new Intent();
        intent.putExtra("type",rawResult.getBarcodeFormat().toString());
        intent.putExtra("data",rawResult.getText());
        setResult(11,intent);
        this.finish();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mScannerView.stopCamera();
    }
}
