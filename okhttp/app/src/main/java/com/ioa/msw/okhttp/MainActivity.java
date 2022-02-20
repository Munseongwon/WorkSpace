package com.ioa.msw.okhttp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText linkEt = findViewById(R.id.link_Et);
        Button searchBtn = findViewById(R.id.search_bar);
        TextView htmlTv = findViewById(R.id.html_tv);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = linkEt.getText().toString();
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(link).build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String html = response.body().string();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                htmlTv.setText(html);
                            }
                        });

                        Log.d("onResponse",html);
                    }
                });
            }
        });
    }
}