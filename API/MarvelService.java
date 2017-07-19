package com.example.juan.marvelheroes.API;

import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Juan on 18/07/2017.
 */

public class MarvelService {

    public static Marvel getMarvelAPI(){

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {

                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        Request originalRequest = chain.request();
                        HttpUrl originalUrl = originalRequest.url();

                        HttpUrl newUrl = originalUrl.newBuilder()
                                .addQueryParameter(Marvel.API_KEY, Marvel.API_KEY_VALUE)
                                .addQueryParameter(Marvel.TIME_STAMP, Marvel.TIME_STAMP_VALUE)
                                .addQueryParameter(Marvel.HASH, Marvel.HASH_VALUE)
                                .build();

                        Request.Builder requestBuilder = originalRequest.newBuilder().url(newUrl);

                        Request request = requestBuilder.build();

                        long t1 = System.nanoTime();
                        Log.i("OkHttp",String.format("Sending request %s on %s%n%s",
                                request.url(), chain.connection(), request.headers()));

                        Response response = chain.proceed(request);

                        long t2 = System.nanoTime();
                        Log.i("OkHttp",String.format("Received response for %s in %.1fms%n%s",
                                response.request().url(), (t2 - t1) / 1e6d, response.headers()) );

                        return response;
                    }
                }).build();

        return new Retrofit.Builder()
                .baseUrl(Marvel.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Marvel.class);
    }
}
