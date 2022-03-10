package com.unikrew.faceoff.ABLPlugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.EditText;

import com.unikrew.faceoff.ABLPlugin.ui.OtpVerificationActivity;

public class OTPReciever extends BroadcastReceiver {

    private static EditText et_otp1;
    private static EditText et_otp2;
    private static EditText et_otp3;
    private static EditText et_otp4;
    private static EditText et_otp5;
    private static EditText et_otp6;

    public void setEditText_otp(EditText et1,
                                EditText et2,
                                EditText et3,
                                EditText et4,
                                EditText et5,
                                EditText et6) {
        OTPReciever.et_otp1 = et1;
        OTPReciever.et_otp2 = et2;
        OTPReciever.et_otp3 = et3;
        OTPReciever.et_otp4 = et4;
        OTPReciever.et_otp5 = et5;
        OTPReciever.et_otp6 = et6;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage[] smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for (SmsMessage smsMessage: smsMessages){
            String msg_body = smsMessage.getMessageBody();
            String get_otp = msg_body.split("code ")[1];
            OtpVerificationActivity.countDownTimer.start();
            et_otp1.setText(String.valueOf(get_otp.charAt(0)));
            et_otp2.setText(String.valueOf(get_otp.charAt(1)));
            et_otp3.setText(String.valueOf(get_otp.charAt(2)));
            et_otp4.setText(String.valueOf(get_otp.charAt(3)));
            et_otp5.setText(String.valueOf(get_otp.charAt(4)));
            et_otp6.setText(String.valueOf(get_otp.charAt(5)));

        }
    }
}
