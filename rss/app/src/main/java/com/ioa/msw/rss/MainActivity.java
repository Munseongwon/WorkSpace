package com.ioa.msw.rss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.stanfy.gsonxml.GsonXml;
import com.stanfy.gsonxml.GsonXmlBuilder;
import com.stanfy.gsonxml.XmlParserCreator;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements NewsAdapter.OnItemClickListener {

    ArrayList<Item> newsList = new ArrayList<>();
    NewsAdapter newsAdapter;
    RecyclerView newsListRv;
    Handler handler = new Handler();

    XmlParserCreator parserCreator = new XmlParserCreator() {
        @Override
        public XmlPullParser createParser() {
            try {
                return XmlPullParserFactory.newInstance().newPullParser();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsListRv = findViewById(R.id.newsList_rv);
        newsAdapter = new NewsAdapter(this,newsList);
        newsListRv.setAdapter(newsAdapter);
        newsAdapter.setOnItemClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        newsListRv.setLayoutManager(layoutManager);

        GsonXml gsonXml = new GsonXmlBuilder()
                .setXmlParserCreator(parserCreator)
                .setSameNameLists(true)
                .create();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://news.sbs.co.kr/news/SectionRssFeed.do?sectionId=01&plink=RSSREADER")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String rssResult = response.body().string();
                Rss rss = gsonXml.fromXml(rssResult,Rss.class);
                for(Item item : rss.getChannel().getItem()){
                    newsList.add(item);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        newsAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }

    @Override
    public void onItemClick(Item item) {
        String link = item.getLink();
        Intent intent = new Intent(Intent.ACTION_VIEW); //link를 띄울 때 뷰를 보인다.
        intent.setData(Uri.parse(link));
        startActivity(intent);
    }
}