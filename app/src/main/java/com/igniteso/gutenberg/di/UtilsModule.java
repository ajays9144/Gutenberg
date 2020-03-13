package com.igniteso.gutenberg.di;

import android.app.Application;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.igniteso.gutenberg.BuildConfig;
import com.igniteso.gutenberg.network.ApiCallInterface;
import com.igniteso.gutenberg.repository.ShowBookRepository;
import com.igniteso.gutenberg.utils.AppConstants;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The type Utils module.
 */
@Module
public class UtilsModule {
    /**
     * Instantiates a new Utils module.
     */
    public UtilsModule() {

    }

    /**
     * Provide gson gson.
     *
     * @return the gson
     */
    @Provides
    @Singleton
    public Gson provideGson() {
        GsonBuilder builder =
                new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return builder.setLenient().create();
    }

    /**
     * Provide retrofit retrofit.
     *
     * @param gson         the gson
     * @param okHttpClient the ok http client
     * @return the retrofit
     */
    @Provides
    @Singleton
    public Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    /**
     * Gets api call interface.
     *
     * @param retrofit the retrofit
     * @return the api call interface
     */
    @Provides
    @Singleton
    ApiCallInterface getApiCallInterface(Retrofit retrofit) {
        return retrofit.create(ApiCallInterface.class);
    }

    /**
     * Gets request header.
     *
     * @return the request header
     */
    @Provides
    @Singleton
    public OkHttpClient getRequestHeader() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE);
        //Uncomment below line to set log level based on build mode
        if (BuildConfig.DEBUG) {
            logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder().build();
            return chain.proceed(request);
        }).connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS);
        return httpClient.build();
    }


    /**
     * Provider show book repository show book repository.
     *
     * @param application      the application
     * @param apiCallInterface the api call interface
     * @return the show book repository
     */
    @Provides
    @Singleton
    ShowBookRepository providerShowBookRepository(Application application, ApiCallInterface apiCallInterface) {
        return new ShowBookRepository(application.getApplicationContext(), apiCallInterface);
    }
}
