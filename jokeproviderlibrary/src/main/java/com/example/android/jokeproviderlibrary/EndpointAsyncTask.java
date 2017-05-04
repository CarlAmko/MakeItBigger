package com.example.android.jokeproviderlibrary;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.example.carl.jokes.jokesbackend.myApi.MyApi;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;

import java.io.IOException;

import timber.log.Timber;

/**
 * Created by Carl on 5/4/2017.
 */

public class EndpointAsyncTask extends AsyncTask<EndpointAsyncTask.EndpointResponseListener, Void, String> {

    private static MyApi myApiService = null;
    private EndpointResponseListener listener;

    private static final int MILLISECONDS_IN_SECOND = 1000;

    public interface EndpointResponseListener {
        void onJokeEndpointResponse(String res);
    }

    @Override
    protected void onPreExecute() {
        Timber.d("onPreExecute");
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(EndpointResponseListener... params) {
        // Only do this once
        if(myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), new HttpRequestInitializer() {
                @Override
                // Set timeout for 5 seconds.
                public void initialize(HttpRequest request) throws IOException {
                    request.setConnectTimeout(MILLISECONDS_IN_SECOND * 5);
                    request.setReadTimeout(MILLISECONDS_IN_SECOND * 5);
                }
            })
                    // 10.0.2.2 is default localhost;s IP address in Android emulator
                    // @TODO -- Edit to connect to your server.
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            myApiService = builder.build();
        }

        // Assign response listener.
        listener = params[0];

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Timber.d("onPostExecute");
        listener.onJokeEndpointResponse(result);
    }

}
