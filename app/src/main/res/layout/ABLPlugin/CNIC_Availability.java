package com.unikrew.faceoff.ABLPlugin;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.unikrew.faceoff.ABLPlugin.model.CnicPostParams;
import com.unikrew.faceoff.ABLPlugin.model.ResponseDTO;
import com.unikrew.faceoff.ABLPlugin.ui.CnicAvailabilityViewModel;
import com.unikrew.faceoff.ABLPlugin.ui.FingerPrintActivity;
import com.unikrew.faceoff.ABLPlugin.ui.OtpVerificationActivity;
import com.ofss.digx.mobile.android.allied.R;

public class CNIC_Availability extends AppCompatActivity {

    private EditText etAccNumber;
    private EditText etCnicNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cnic_availability);


        etAccNumber = findViewById(R.id.et_accNumber);
        etCnicNumber = findViewById(R.id.et_cnicNumber);
    }

    public void postCustomerDetail(View view) throws InterruptedException {


        if (isEmpty(etAccNumber) ||
                isEmpty(etCnicNumber)){
            showAlert(Config.errorType,"Please fill all * fields");
            return;
        }
        if (etAccNumber.getText().length() < Config.ACCOUNT_LENGTH){
            showAlert(Config.errorType,"Account Number Length is not valid");
            return;
        }else if (etCnicNumber.getText().length() < Config.CNIC_LENGTH){
            showAlert(Config.errorType,"CNIC Length is not valid");
            return;
        }else if (!isOnline()){
            showAlert(Config.errorType,"No Internet connection!");
            return;
        }

        CnicPostParams CnicPostParams = new CnicPostParams();

        CnicPostParams.getData().setCnic(etCnicNumber.getText().toString());
        CnicPostParams.getData().setAccountNo(etAccNumber.getText().toString());

        CnicAvailabilityViewModel vm = new CnicAvailabilityViewModel();
        vm.postCNIC(CnicPostParams, CNIC_Availability.this);


        vm.CnicVerifiedLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showAlert(Config.successType,s);
            }
        });

        vm.CnicSuccessLiveData.observe(this, new Observer<ResponseDTO>() {
            @Override
            public void onChanged(ResponseDTO responseDTO) {
                Intent i = new Intent(view.getContext(), OtpVerificationActivity.class);
                i.putExtra(Config.RESPONSE,responseDTO);
                i.putExtra(Config.CNIC_ACC,CnicPostParams);
                startActivity(i);
                clearFields();
            }
        });

        vm.CnicErrorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String responseMsg) {
                showAlert(Config.errorType,responseMsg);
            }
        });
    }

    private void clearFields() {
        etAccNumber.setText("");
        etCnicNumber.setText("");
    }

    public Boolean isEmpty(EditText et) {
        if (et.getText().toString().equals("")){
            return true;
        }
        return false;
    }

    public void cancelActivity(View view) {
//        finish();
        startActivity(new Intent(CNIC_Availability.this, FingerPrintActivity.class));
    }

    public void showAlert(int type,String msg){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(CNIC_Availability.this);
        if (type == Config.errorType){
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.RED);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("ERROR");
            spannableStringBuilder.setSpan(
                    foregroundColorSpan,
                    0,
                    "ERROR".length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
            builder1.setTitle(spannableStringBuilder);
        }else if (type==Config.successType){
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.GREEN);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("VERIFIED");
            spannableStringBuilder.setSpan(
                    foregroundColorSpan,
                    0,
                    "VERIFIED".length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
            builder1.setTitle(spannableStringBuilder);

        }

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
}