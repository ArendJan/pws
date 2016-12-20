package com.svshizzle.pws.smartfridge.activity;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.svshizzle.pws.smartfridge.R;
import com.svshizzle.pws.smartfridge.api.Smartfridge;

public class JobFragment extends Fragment implements android.view.View.OnClickListener {
    private int barcodeCounter = 0;
    private ProgressDialog dialog;
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
                //TODO:Hier nog iets?
                break;
        }

    }

    public void print(){
        dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Sending the printjob to the server...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
        EditText editText = (EditText) getActivity().findViewById(R.id.jobs_print_editText);
        String text = editText.getText().toString();
        Smartfridge smartfridge = new Smartfridge(getActivity()){
            @Override
            public void printJobDone() {
                Toast.makeText(getActivity(), getString(R.string.jobs_print_text_done), Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }

            @Override
            public void printJobError(String e) {

                dialog.dismiss();
                Toast.makeText(getActivity(), "Oops, something went wrong. Errormessage:"+e, Toast.LENGTH_LONG).show();
            }
        };
        smartfridge.printJob(text);
    }

    public void shutdown(){
        dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Sending the shutdown command to the server...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
        Smartfridge smartfridge = new Smartfridge(getActivity()){
            @Override
            public void shutdownDone() {
                Toast.makeText(getActivity(), getString(R.string.jobs_shutdown_text_done), Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }

            @Override
            public void shutdownError(String e) {

                dialog.dismiss();
                Toast.makeText(getActivity(), "Oops, something went wrong. Errormessage:"+e, Toast.LENGTH_LONG).show();
            }
        };
        smartfridge.shutdown();
    }

    public void restart(){
        dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Sending the restart command to the server...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
        Smartfridge smartfridge = new Smartfridge(getActivity()){
            @Override
            public void restartDone() {
                Toast.makeText(getActivity(), getString(R.string.jobs_restart_text_done), Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }

            @Override
            public void restartError(String e) {
                dialog.dismiss();
                Toast.makeText(getActivity(), "Oops, something went wrong. Errormessage:"+e, Toast.LENGTH_LONG).show();
            }
        };
        smartfridge.restart();
    }
    private boolean getBarcodeInt(){
        barcodeCounter++;
        return barcodeCounter!=2;

    }
    public void barcode(){
        dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Thinking of a barcode...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        final Smartfridge smartfridge = new Smartfridge(getActivity()){
            @Override
            public void barcodeDone() {

                if(getBarcodeInt()){
                    dialog.setMessage("Barcode job done. Adding item to database...");
                }else{
                    dialog.dismiss();
                }
                Toast.makeText(getActivity(), getString(R.string.jobs_barcode_text_done), Toast.LENGTH_LONG).show();
            }

            @Override
            public void barcodeError(String e) {
                if(getBarcodeInt()){
                    dialog.setMessage("Barcode job had an error: "+e + ". Adding item to database...");
                }else{
                    dialog.dismiss();
                }


            }

            @Override
            public void createItemDone() {
                if(getBarcodeInt()){
                    dialog.setMessage("Item added to database. Still working on the barcode job...");
                }else{
                    dialog.dismiss();
                }
                Toast.makeText(getActivity(), getString(R.string.jobs_barcode_text_create_done), Toast.LENGTH_LONG).show();
            }

            @Override
            public void createItemError(String e) {//TODO:fix als geen internet heeft en beide tegelijkertijd de getbarcodeint hebben opgevraagd, waardoor dialog blijft staan....

                if(getBarcodeInt()){
                    dialog.setMessage("Barcode job error:"+e+". Adding item to database...");
                }else{
                    dialog.dismiss();
                }
            }
        };

        EditText editText = (EditText) getActivity().findViewById(R.id.jobs_barcode_edittext);
        String title = editText.getText().toString();
        String barcode = smartfridge.createBarcode();
        dialog.setMessage("Sending a request to the server to print and a request to add the item to the database...");
        Toast.makeText(getActivity(), barcode, Toast.LENGTH_LONG).show();
        smartfridge.barcode(barcode);
        smartfridge.createItem(barcode, title);
    }


}

