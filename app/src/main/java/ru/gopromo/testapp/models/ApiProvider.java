package ru.gopromo.testapp.models;


import android.util.Log;

import com.einmalfel.earl.EarlParser;
import com.einmalfel.earl.Feed;
import com.einmalfel.earl.Item;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.zip.DataFormatException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import ru.gopromo.testapp.BuildConfig;

public class ApiProvider {

    public static Api getApi(String baseUrl) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.interceptors().add(interceptor);
        }

        Retrofit.Builder builder = new Retrofit.Builder().
                baseUrl(baseUrl)
                .addConverterFactory(new Converter.Factory() {
                    @Override
                    public Converter<ResponseBody, List<? extends Item>> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                        return new RssConverter();
                    }
                })
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        builder.client(httpClientBuilder.build());
        return builder.build().create(Api.class);
    }

    private static class RssConverter implements Converter<ResponseBody, List<? extends Item>> {
        @Override
        public List<? extends Item> convert(ResponseBody value) throws IOException {
            List<? extends Item> result = null;
            InputStream is = value.byteStream();
            try {
                Feed feed = EarlParser.parseOrThrow(is, 0);
                result = feed.getItems();
            } catch (XmlPullParserException | DataFormatException e) {
                e.printStackTrace();
            } finally {
                value.close();
            }
            return result;
        }
    }
}
