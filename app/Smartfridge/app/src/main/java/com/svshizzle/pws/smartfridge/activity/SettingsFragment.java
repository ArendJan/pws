
package com.svshizzle.pws.smartfridge.activity;

        import android.app.Activity;
        import android.app.Fragment;


        import android.os.AsyncTask;
        import android.os.Bundle;

        import android.support.v4.widget.SwipeRefreshLayout;

        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ListView;



        import com.svshizzle.pws.smartfridge.R;
        import com.svshizzle.pws.smartfridge.adapter.HomeListAdapter;
        import com.svshizzle.pws.smartfridge.api.Smartfridge;
        import com.svshizzle.pws.smartfridge.model.Item;

        import com.svshizzle.pws.smartfridge.request.RequestClassPost;
        import com.svshizzle.pws.smartfridge.request.RequestReturn;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;

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

