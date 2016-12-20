package com.svshizzle.pws.smartfridge.activity;

import android.app.Activity;
import android.app.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.svshizzle.pws.smartfridge.R;

public class DefaultFragment extends Fragment {


    public DefaultFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_home_fragment, container, false);




        return rootView;
    }




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }




}

