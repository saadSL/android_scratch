package com.unikrew.faceoff.ABLPlugin.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import com.unikrew.faceoff.Config;
import com.unikrew.faceoff.ABLPlugin.model.CnicPostParams;
import com.unikrew.faceoff.ABLPlugin.model.OtpPostParams;
import com.unikrew.faceoff.ABLPlugin.model.OtpResponse;
import com.unikrew.faceoff.ABLPlugin.model.ResponseDTO;
import com.ofss.digx.mobile.android.allied.R;
import com.unikrew.faceoff.OTPReciever;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class OtpVerificationActivity extends AppCompatActivity implements TextWatcher {

    private EditText otp1;
    private EditText otp2;
    private EditText otp3;
    private EditText otp4;
    private EditText otp5;
    private EditText otp6;

    private Button btnVerify;

    private TextView mobileNumber;

    private TextView timer;

    ResponseDTO res;
    CnicPostParams cnicPostParams;

    public static CountDownTimer countDownTimer;

    boolean otpStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_verification);

        bind();
        set();
        requestPermission();

        new OTPReciever().setEditText_otp(
                otp1,otp2,otp3,
                otp4,otp5,otp6
        );

        startTimer(Config.countDownTime);
    }





    private void set() {
        res = (ResponseDTO) getIntent().getSerializableExtra(Config.RESPONSE);
        mobileNumber.setText("03XX-XXXX"+res.getData().getMobileNo().substring(res.getData().getMobileNo().length()-3));
        cnicPostParams = (CnicPostParams) getIntent().getSerializableExtra(Config.CNIC_ACC);
        otp6.addTextChangedListener(this);
    }

    private void bind() {
        otp1 = findViewById(R.id.et_otp1);
        otp2 = findViewById(R.id.et_otp2);
        otp3 = findViewById(R.id.et_otp3);
        otp4 = findViewById(R.id.et_otp4);
        otp5 = findViewById(R.id.et_otp5);
        otp6 = findViewById(R.id.et_otp6);


        btnVerify = findViewById(R.id.btn_verify);

        mobileNumber = findViewById(R.id.tv_mobileNumber);

        timer = findViewById(R.id.timer);
    }

    private void startTimer(int totalTime) {
        countDownTimer = new CountDownTimer(totalTime,1000) {
            @Override
            public void onTick(long l) {
                int minutes = (int)(l/1000)/60;
                int seconds = (int)(l/1000)%60;
                String timeFormat = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
                if (minutes==0){
                    timer.setText(timeFormat+" seconds");
                }else{
                    timer.setText(timeFormat+" minutes");
                }

            }

            @Override
            public void onFinish() {
                showAlert("You OTP Expired");
                countDownTimer.cancel();
            }
        }.start();
    }

    public void OTPVerification(View view) throws InterruptedException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        if (isEmpty(otp1) || isEmpty(otp4) ||
                isEmpty(otp2) || isEmpty(otp5) ||
                isEmpty(otp3) || isEmpty(otp6)) {
            showAlert("OTP fields empty");
            return;
        }else if (!isOnline()){
            showAlert("No internet connection !!!");
            return;
        }

        String otp = otp1.getText().toString()+otp2.getText().toString()+otp3.getText().toString()+otp4.getText().toString()+otp5.getText().toString()+otp6.getText().toString();

        OtpPostParams otpPostParams = new OtpPostParams();

        otpPostParams.getData().setOtp(encrypt(otp));
        otpPostParams.getData().setRdaCustomerProfileId(""+res.getData().getEntityId());

       CnicAvailabilityViewModel vm = new CnicAvailabilityViewModel();
       vm.postOtp(otpPostParams,res.getData().getAccessToken(),this);


       vm.OtpSuccessLiveData.observe(this, new Observer<OtpResponse>() {
           @Override
           public void onChanged(OtpResponse otpResponse) {
               Intent i = new Intent(view.getContext(),FingerPrintActivity.class);
               i.putExtra(Config.RESPONSE,otpResponse);
               i.putExtra("TOKEN",res.getData().getAccessToken());
               startActivity(i);
               countDownTimer.cancel();
               clearFields();
           }
       });

       vm.OtpErrorLiveData.observe(this, new Observer<String>() {
           @Override
           public void onChanged(String errorMsg) {
                showAlert(errorMsg);
                clearFields();
           }
       });
//        startActivity(new Intent(OtpVerificationActivity.this, FingerPrintActivity.class));
    }

    private void clearFields() {
        otp1.setText("");
        otp2.setText("");
        otp3.setText("");
        otp4.setText("");
        otp5.setText("");
        otp6.setText("");
    }

    public Boolean isEmpty(EditText et) {
        if (et.getText().toString().equals("") || et.getText().toString().equals("-")) {
            return true;
        }
        return false;
    }

    public void cancelActivity(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }

    public void sendOtp(View view) {
        CnicAvailabilityViewModel viewModel = new CnicAvailabilityViewModel();
        try {
            viewModel.postCNIC(cnicPostParams,this);
            countDownTimer.start();

            viewModel.CnicSuccessLiveData.observe(this, new Observer<ResponseDTO>() {
                @Override
                public void onChanged(ResponseDTO responseDTO) {
                    showAlert("OTP Request Send");
                }
            });

            viewModel.CnicErrorLiveData.observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    showAlert(s);
                }
            });


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void messageFunc(View view) {
        showAlert("Sorry, currently the function is not responsive !!!");
    }

    public void powerSettingFunc(View view) {
        showAlert("Sorry, currently the function is not responsive !!!");
    }

    @SuppressLint("NewApi")
    public String encrypt(String value)  {

            String initVector = "0000000000000000";
            String key = "4dweqdxcerfvc3rw";
        IvParameterSpec ivParameterSpec = null;
        try {
            ivParameterSpec = new IvParameterSpec(initVector.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        SecretKeySpec secretKeySpec = null;
        try {
            secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"),"AES");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        try {
            cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec,ivParameterSpec);
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        byte[] encrypted = new byte[0];
        try {
            encrypted = cipher.doFinal(value.getBytes());
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(encrypted);
    }



    public void showAlert(String msg){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(OtpVerificationActivity.this);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            return false;
        }
        return true;
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(OtpVerificationActivity.this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(OtpVerificationActivity.this,new String[]{
                    Manifest.permission.RECEIVE_SMS
            },100);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        System.out.println("Do Nothing!!!");
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        try {
            if (otp6.getText().length()>0){
                OTPVerification(btnVerify);
            }

        } catch (InterruptedException | UnsupportedEncodingException | BadPaddingException | NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        System.out.println("After Text changed : ");
    }
}