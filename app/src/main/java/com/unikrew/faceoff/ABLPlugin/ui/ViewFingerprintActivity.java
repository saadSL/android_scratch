package com.unikrew.faceoff.ABLPlugin.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ofss.digx.mobile.android.allied.R;
import com.unikrew.faceoff.Config;
import com.unikrew.faceoff.fingerprint.FingerprintHelpers.Png;
import com.unikrew.faceoff.fingerprint.FingerprintResponse;
import com.unikrew.faceoff.fingerprint.ResultIPC;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ViewFingerprintActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fingerprints);

        int responseCode = getIntent().getIntExtra(Config.KEY_RESPONSE_CODE, -1);
        if (responseCode > 0) {
            FingerprintResponse fingerprintResponse = ResultIPC.getInstance().getFingerprintResponse(responseCode);

//            if(fingerprintResponse != null
//                    && fingerprintResponse.getWsqList() != null
//                    && !fingerprintResponse.getWsqList().isEmpty()){
//
//                for (Wsq wsq : fingerprintResponse.getWsqList()) {
//                    saveWsqToLocalStorage(ViewFingerprintActivity.this, wsq.getBinaryBase64ObjectWSQ(), String.valueOf(wsq.getFingerPositionCode()), "Fingerprints/WSQ/");
//                }
//                toastMessage("WSQ save to LocalStorage");
//            }

            if (fingerprintResponse != null
                    && fingerprintResponse.getPngList() != null
                    && !fingerprintResponse.getPngList().isEmpty()) {

                for (Png png : fingerprintResponse.getPngList()) {

                    //saveWsqToLocalStorage(ViewFingerprintActivity.this, png.getBinaryBase64ObjectPNG(), String.valueOf(png.getFingerPositionCode()), "Fingerprints/PNG/");

                    String binaryBase64ObjectPNG = png.getBinaryBase64ObjectPNG();
                    if (binaryBase64ObjectPNG != null && !binaryBase64ObjectPNG.isEmpty()) {
                        byte[] decodedString = Base64.decode(binaryBase64ObjectPNG, Base64.DEFAULT);
                        Bitmap decodedImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        setBitmap(decodedImage, png.getFingerPositionCode());
                    }
                }

                //toastMessage("PNG save to LocalStorage");

            } else {
                toastMessage("No fingerprints were captured or PNGs were not packed!");
            }
        } else {
            toastMessage("No fingerprints captured");
        }
    }

    /**
     * Renders bitmaps to imageviews
     * All imageview and their respective descriptions are hide by default
     * If a finerprint is available, then we show fingerprint and its description
     *
     * @param decodedImage
     * @param fingerPositionCode
     */
    private void setBitmap(Bitmap decodedImage, Integer fingerPositionCode) {
        switch (fingerPositionCode) {

            // Right Thumb
            case 1:
                ((ImageView) findViewById(R.id.finger1IV)).setImageBitmap(decodedImage);
                findViewById(R.id.finger1Layout).setVisibility(View.VISIBLE);
                break;

            // Right Index Finger
            case 2:
                ((ImageView) findViewById(R.id.finger2IV)).setImageBitmap(decodedImage);
                findViewById(R.id.finger2Layout).setVisibility(View.VISIBLE);
                break;

            // Right Middle Finger
            case 3:
                ((ImageView) findViewById(R.id.finger3IV)).setImageBitmap(decodedImage);
                findViewById(R.id.finger3Layout).setVisibility(View.VISIBLE);
                break;

            // Right Ring Finger
            case 4:
                ((ImageView) findViewById(R.id.finger4IV)).setImageBitmap(decodedImage);
                findViewById(R.id.finger4Layout).setVisibility(View.VISIBLE);
                break;

            // Right Little Finger
            case 5:
                ((ImageView) findViewById(R.id.finger5IV)).setImageBitmap(decodedImage);
                findViewById(R.id.finger5Layout).setVisibility(View.VISIBLE);
                break;

            // Left Thumb
            case 6:
                ((ImageView) findViewById(R.id.finger6IV)).setImageBitmap(decodedImage);
                findViewById(R.id.finger6Layout).setVisibility(View.VISIBLE);
                break;


            // Left Index Finger
            case 7:
                ((ImageView) findViewById(R.id.finger7IV)).setImageBitmap(decodedImage);
                findViewById(R.id.finger7Layout).setVisibility(View.VISIBLE);
                break;


            // Left Middle Finger
            case 8:
                ((ImageView) findViewById(R.id.finger8IV)).setImageBitmap(decodedImage);
                findViewById(R.id.finger8Layout).setVisibility(View.VISIBLE);
                break;


            // Left Ring Finger
            case 9:
                ((ImageView) findViewById(R.id.finger9IV)).setImageBitmap(decodedImage);
                findViewById(R.id.finger9Layout).setVisibility(View.VISIBLE);
                break;


            // Left Little Finger
            case 10:
                ((ImageView) findViewById(R.id.finger10IV)).setImageBitmap(decodedImage);
                findViewById(R.id.finger10Layout).setVisibility(View.VISIBLE);
                break;
            default:
                return;
        }
    }

    /**
     * Toast error if no fingerprint found
     */
    private void toastMessage(String message) {
        Toast.makeText(this, message,
                Toast.LENGTH_SHORT).show();
    }

    public void saveWsqToLocalStorage(Context context, String fileContent, String fileName, String directory) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.US);
            String currentDateandTime = sdf.format(new Date());

            File root = new File(Environment.getExternalStorageDirectory(), directory + Config.CNIC_NUMBER);

            if (!root.exists()) {
                root.mkdirs();
            }
            else {
                root.deleteOnExit();
                root.mkdirs();
            }

            File wsqfile = new File(root, fileName+".txt");
            FileWriter writer = new FileWriter(wsqfile);
            writer.append(fileContent);
            writer.flush();
            writer.close();


        } catch (IOException e) {
            toastMessage("Unable to save fingerprints to local storage.");
        }
    }
}
