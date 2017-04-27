package gank.qhm.com.hmgank.Dao;

import gank.qhm.com.hmgank.Config;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by qhm on 2017/4/13
 */

public class HMGankServer {

    private static final String baseUrl = Config.API;

    private static HMGankApi hmGankApi;
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor
            (new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build();

    public static HMGankApi getApi() {
        if (hmGankApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            hmGankApi = retrofit.create(HMGankApi.class);

        }
        return hmGankApi;
    }

}
