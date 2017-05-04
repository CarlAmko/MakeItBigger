package com.example.android.jokeproviderlibrary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import timber.log.Timber;

public class JokeFragment extends Fragment implements EndpointAsyncTask.EndpointResponseListener {

    private Toast currentToast;
    Button getJokeButton;
    public JokeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_joke, container, false);

        getJokeButton = (Button) root.findViewById(R.id.b_joke);
        Timber.d(getJokeButton.toString());

        getJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("Joke button clicked!");
                EndpointAsyncTask task = new EndpointAsyncTask();
                task.addListener(JokeFragment.this);
                task.execute();
            }
        });


        return root;
    }

    @Override
    public void onJokeEndpointResponse(String res) {
        // If previous current toast is valid, hide it.
        if(currentToast != null) {
            currentToast.cancel();
        }

        // Update and show current toast.
        currentToast = Toast.makeText(getActivity().getApplicationContext(), res, Toast.LENGTH_LONG);
        currentToast.show();
    }
}
