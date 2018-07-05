package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    private static final int REQUEST_OK = 200;
    @BindView(R.id.btnCompose) Button btnCompose;
    @BindView(R.id.etCompose) EditText etCompose;
    @BindView(R.id.btnCancel) ImageButton btnCancel;
    @BindView(R.id.tvCharCount) TextView tvCharCount;
    Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_nobackground);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("Compose Tweet");

        ButterKnife.bind(this);

        btnCompose.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                onSubmit(v);
            }
        });

        btnCancel.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                closeCompose();
            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        String charsLeft = String.format(Locale.US, "%d", 140 - etCompose.getText().toString().length());
        tvCharCount.setText(charsLeft);
        return true;
    }

    public void onSubmit(View v) {

        // make sure the text is <= 160 characters
        // etc validation checks
        String message = etCompose.getText().toString();
        if(message.length() <= 140){

            TwitterClient client = TwitterApp.getRestClient(this);
            client.sendTweet(message, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        tweet = Tweet.fromJSON(response);
                        // Prepare data intent
                        Intent data = new Intent();
                        // Pass relevant data back as a result
                        data.putExtra("tweet", Parcels.wrap(tweet));
                        // Activity finished ok, return the data
                        setResult(RESULT_OK, data); // set result code and bundle data for response
                        finish(); // closes the activity, pass data to parent
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });

        }

    }

    public void closeCompose(){
        Intent data = new Intent();
        setResult(RESULT_CANCELED, data);
        finish();
    }


}
