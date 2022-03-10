package com.unikrew.faceoff.ABLPlugin.ui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unikrew.faceoff.ABLPlugin.interfaces.RetrofitApi;
import com.unikrew.faceoff.ABLPlugin.model.CnicPostParams;
import com.unikrew.faceoff.ABLPlugin.model.OtpPostParams;
import com.unikrew.faceoff.ABLPlugin.model.OtpResponse;
import com.unikrew.faceoff.ABLPlugin.model.ResponseDTO;
import com.unikrew.faceoff.ABLPlugin.model.UpdateBioMetricStatusPostParams;
import com.unikrew.faceoff.ABLPlugin.model.UpdateBioMetricStatusResponse;
import com.unikrew.faceoff.Config;
import com.ofss.digx.mobile.android.allied.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CnicAvailabilityViewModel {


    private RetrofitApi service;

    public MutableLiveData<ResponseDTO> CnicSuccessLiveData = new MutableLiveData<ResponseDTO>();
    public MutableLiveData<String> CnicErrorLiveData = new MutableLiveData<String>();
    public MutableLiveData<String> CnicVerifiedLiveData = new MutableLiveData<String>();

    public MutableLiveData<OtpResponse> OtpSuccessLiveData = new MutableLiveData<OtpResponse>();
    public MutableLiveData<String> OtpErrorLiveData = new MutableLiveData<String>();

    public MutableLiveData<UpdateBioMetricStatusResponse> BioMetricStatusSuccessLiveData = new MutableLiveData<UpdateBioMetricStatusResponse>();
    public MutableLiveData<String> BioMetricStatusErrorLiveData = new MutableLiveData<String>();



    Activity activity;
    AlertDialog loader;


   public CnicAvailabilityViewModel(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        service = retrofit.create(RetrofitApi.class);
    }

    public void postCNIC(CnicPostParams cd, Activity myActivity) throws InterruptedException {
        this.activity = myActivity;

        Call<ResponseDTO> callableRes = service.CNICpost(cd);
        callableRes.enqueue(new Callback<ResponseDTO>() {
            @Override
            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                if (response.code()==200){
                    CnicSuccessLiveData.postValue(response.body());
                    loader.dismiss();
                }else if (response.code()==403){
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject message = jObjError.getJSONObject("message");
                        if (message.getString("status").equals("api_error")){
                            CnicErrorLiveData.postValue(message.getString("errorDetail"));
                            loader.dismiss();
                        }else{
                            if (message.getString("status").equals("409")){
                                CnicVerifiedLiveData.postValue(message.getString("description"));
                            }else if (message.getString("status").equals("401")){
                                CnicErrorLiveData.postValue(message.getString("description"));
                            }

                            loader.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                CnicErrorLiveData.postValue(t.getMessage());
                loader.dismiss();
            }
        });
        showLoading();
        loader.show();
    }

    public void postOtp(OtpPostParams postParams, String accessToken, Activity myActivity) throws InterruptedException{
        this.activity = myActivity;
        Call<OtpResponse> callableRes = service.OtpPost(postParams,"Bearer "+accessToken);
        callableRes.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                if (response.code()==200){
                    OtpSuccessLiveData.postValue(response.body());
                    loader.dismiss();
                }else if (response.code()==403){
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String msg = jObjError.getJSONObject("message").getString("description");
                        OtpErrorLiveData.postValue(msg);
                        loader.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                System.out.println(t.getLocalizedMessage());
                OtpErrorLiveData.postValue(t.getMessage());
                loader.dismiss();
            }
        });
        showLoading();
        loader.show();
    }

    public void updateBioMetricStatus(UpdateBioMetricStatusPostParams pp, String accessToken, Activity myActivity){
        this.activity = myActivity;
        Call<UpdateBioMetricStatusResponse> callableRes = service.UpdateBioMetricStatus(pp,"Bearer "+accessToken);
        callableRes.enqueue(new Callback<UpdateBioMetricStatusResponse>() {
            @Override
            public void onResponse(Call<UpdateBioMetricStatusResponse> call, Response<UpdateBioMetricStatusResponse> response) {
                if (response.code()==200){
                    BioMetricStatusSuccessLiveData.postValue(response.body());
                    loader.dismiss();
                }else if (response.code()==403){
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String msg = jObjError.getJSONObject("message").getString("description");
                        BioMetricStatusErrorLiveData.postValue(msg);
                        loader.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<UpdateBioMetricStatusResponse> call, Throwable t) {
                BioMetricStatusErrorLiveData.postValue(t.getMessage());
                loader.dismiss();
            }
        });
        showLoading();
        loader.show();
    }

    private void showLoading(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setView(View.inflate(activity, R.layout.loader,null));
        builder1.setCancelable(false);
        loader = builder1.create();
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
