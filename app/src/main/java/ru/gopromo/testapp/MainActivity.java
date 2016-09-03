package ru.gopromo.testapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.einmalfel.earl.EarlParser;
import com.einmalfel.earl.Feed;
import com.einmalfel.earl.Item;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import java.util.zip.DataFormatException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        clientBuilder.connectTimeout(20, TimeUnit.SECONDS);
        clientBuilder.readTimeout(20, TimeUnit.SECONDS);
        OkHttpClient client = clientBuilder.build();

        Request request = new Request.Builder()
                .url("https://lenta.ru/rss/news")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = response.body().byteStream();
                try {
                    Feed feed = EarlParser.parseOrThrow(is, 0);
                    Log.i(TAG, "Processing feed: " + feed.getTitle());
                    for (Item item : feed.getItems()) {
                        String title = item.getTitle();
                        Log.i(TAG, "Item title: " + (title == null ? "N/A" : title));
                    }
                } catch (XmlPullParserException | DataFormatException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
