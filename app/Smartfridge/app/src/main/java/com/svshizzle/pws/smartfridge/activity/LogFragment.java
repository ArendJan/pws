package com.svshizzle.pws.smartfridge.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.svshizzle.pws.smartfridge.R;
import com.svshizzle.pws.smartfridge.api.Smartfridge;


public class LogFragment extends Fragment {


    public LogFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_home_fragment, container, false);

        Smartfridge smartfridge = new Smartfridge(getActivity());
        smartfridge.getLog();



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

