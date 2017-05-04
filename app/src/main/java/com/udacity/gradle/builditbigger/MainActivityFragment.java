package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.jokeproviderlibrary.EndpointAsyncTask;

import timber.log.Timber;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements EndpointAsyncTask.EndpointResponseListener {

    Button getJokeButton;
    public MainActivityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_joke, container, false);

        getJokeButton = (Button) root.findViewById(com.example.android.jokeproviderlibrary.R.id.b_joke);
        Timber.d(getJokeButton.toString());

        getJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("Joke button clicked!");
                new EndpointAsyncTask().execute(MainActivityFragment.this);
            }
        });


        return root;
    }

    @Override
    public void onJokeEndpointResponse(String res) {
        Toast.makeText(getActivity().getApplicationContext(), res, Toast.LENGTH_LONG).show();
    }
}
