package com.example.easymeal.getToken;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.internal.HttpClient;

public class GetToken extends AsyncTask<String, Void, String> {

    ProgressDialog mDialog;
    Context context;
    Activity activity;
    String token = null;
    final String API_GET_TOKEN = "http://192.168.0.9/braintree/main.php";

    public AsyncResponse delegate = null;

    public GetToken(Context context, Activity activity, AsyncResponse delegate) {
        this.context = context;
        this.activity = activity;
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpClient client = new HttpClient();
        client.get(API_GET_TOKEN, new HttpResponseCallback() {
            @SuppressLint("SetTextI18n")
            @Override
            public void success(String responseBody) {
                activity.runOnUiThread(() -> {
                    token = responseBody;
                    delegate.processFinish(token);
                });
            }

            @Override
            public void failure(Exception exception) {
                Log.d("EDMT_ERROR", exception.toString());
            }
        });
        return token;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = new ProgressDialog(this.context, android.R.style.Theme_DeviceDefault_Dialog);
        mDialog.setCancelable(false);
        mDialog.setMessage("Please Wait");
        mDialog.show();
    }

    @Override
    protected void onPostExecute(String result) {
        mDialog.dismiss();
    }
}