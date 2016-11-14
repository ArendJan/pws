package com.svshizzle.pws.smartfridge.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.svshizzle.pws.smartfridge.R;
import com.svshizzle.pws.smartfridge.api.Smartfridge;

public class JobFragment extends Fragment implements android.view.View.OnClickListener {


    public JobFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_job_fragment, container, false);
        Button restartButton = (Button) rootView.findViewById(R.id.jobs_restart_button);
        Button shutdownButton = (Button) rootView.findViewById(R.id.jobs_shutdown_button);
        Button printButton = (Button) rootView.findViewById(R.id.jobs_print_button);
        Button barcodeButton = (Button) rootView.findViewById(R.id.jobs_barcode_button);
        restartButton.setOnClickListener(this);
        shutdownButton.setOnClickListener(this);
        printButton.setOnClickListener(this);
        barcodeButton.setOnClickListener(this);


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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jobs_print_button:
                print();
                break;
            case R.id.jobs_restart_button:
                restart();
                break;
            case R.id.jobs_shutdown_button:
                shutdown();
                break;
            case R.id.jobs_barcode_button:
                barcode();
                break;

            default:
                Log.d("oops", "default is not ok");
                break;
        }

    }

    public void print(){
        EditText editText = (EditText) getActivity().findViewById(R.id.jobs_print_editText);
        String text = editText.getText().toString();
        Smartfridge smartfridge = new Smartfridge(getActivity()){
            @Override
            public void printJobDone() {
                Toast.makeText(getActivity(), getString(R.string.jobs_print_text_done), Toast.LENGTH_LONG).show();
            }

            @Override
            public void printJobError(String e) {
                Log.d("printjoberror", e);
            }
        };
        smartfridge.printJob(text);
    }

    public void shutdown(){
        Smartfridge smartfridge = new Smartfridge(getActivity()){
            @Override
            public void shutdownDone() {
                Toast.makeText(getActivity(), getString(R.string.jobs_shutdown_text_done), Toast.LENGTH_LONG).show();
            }

            @Override
            public void shutdownError(String e) {
                Log.d("shutdownerror",e);
            }
        };
        smartfridge.shutdown();
    }

    public void restart(){
        Smartfridge smartfridge = new Smartfridge(getActivity()){
            @Override
            public void restartDone() {
                Toast.makeText(getActivity(), getString(R.string.jobs_restart_text_done), Toast.LENGTH_LONG).show();
            }

            @Override
            public void restartError(String e) {
                Log.d("restarterror", e);
            }
        };
        smartfridge.restart();
    }

    public void barcode(){
        final Smartfridge smartfridge = new Smartfridge(getActivity()){
            @Override
            public void barcodeDone() {
                Toast.makeText(getActivity(), getString(R.string.jobs_barcode_text_done), Toast.LENGTH_LONG).show();
            }

            @Override
            public void barcodeError(String e) {
                Log.d("barcodeerror", e);

            }

            @Override
            public void createItemDone() {
                Toast.makeText(getActivity(), getString(R.string.jobs_barcode_text_create_done), Toast.LENGTH_LONG).show();
            }

            @Override
            public void createItemError(String e) {
                Log.d("createError", e);
            }
        };

        EditText editText = (EditText) getActivity().findViewById(R.id.jobs_barcode_edittext);
        String title = editText.getText().toString();
        String barcode = smartfridge.createBarcode();
        Toast.makeText(getActivity(), barcode, Toast.LENGTH_LONG).show();
        Log.d("barcode", barcode);
        smartfridge.barcode(barcode);
        smartfridge.createItem(barcode, title);
    }


}

