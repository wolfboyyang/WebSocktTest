package com.huawei.websocketclienttest;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by youi1 on 2017/3/13.
 */

public class Elastic extends AsyncTask<String, Void, String> {
    private static final String fetchUrl = "https://www.baidu.com";
    private static final OkHttpClient client = new OkHttpClient();
    private static final String SEPARATOR = "/";

    private static final String app = "elastictest";

    private static final String elasticsearchHost = "http://13.124.68.183:9200";

    public void fetchByOkhttpAsync(CountDownLatch latch) throws Exception {
        Request request = new Request.Builder()
                .url(fetchUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                response.body().string();
                latch.countDown();
            }
        });
    }

    public static String testPost(String message) {
        try {
            String response = post(getURL("posttest"), "{\"text\":\"" + message + "\"}");
            Log.i("Elastic", "test post response:" + response);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String testDelete(String message) {
        try {
            String response = delete(getURL("posttest", "1"));
            Log.i("Elastic", "test post response:" + response);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String delete(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String getURL(String type) {

        return elasticsearchHost + SEPARATOR + app + SEPARATOR + type + SEPARATOR;
    }

    public static String getURL(String type, String id) {

        return elasticsearchHost + SEPARATOR + app + SEPARATOR + type + SEPARATOR + id;
    }

    @Override
    protected String doInBackground(String... params) {
        return testDelete(params[0]);
        //return testPost(params[0]);
    }
}
