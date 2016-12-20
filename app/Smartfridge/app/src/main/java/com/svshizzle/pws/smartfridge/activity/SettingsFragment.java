
package com.svshizzle.pws.smartfridge.activity;

        import android.app.Activity;
        import android.app.Fragment;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import com.svshizzle.pws.smartfridge.R;

public class SettingsFragment extends Fragment {


    public SettingsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_home_fragment, container, false);


//TODO;Wss moet ik hier iets doen

        return rootView;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }




}

