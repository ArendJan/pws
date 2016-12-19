package com.svshizzle.pws.smartfridge.activity;

import android.app.*;
import android.content.Intent;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import android.view.View;

import com.svshizzle.pws.smartfridge.R;
import com.svshizzle.pws.smartfridge.api.Smartfridge;


public class MainActivity extends ActionBarActivity implements FragmentDrawer.FragmentDrawerListener {



    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginCheck();
        setContentView(R.layout.activity_main);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);
    }



    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {

        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = new JobFragment();
                title = getString(R.string.title_jobs);
                break;
            case 2:
                fragment = new LogFragment();
                title = getString(R.string.title_log);
                break;
            case 3:
                fragment = new ListFragment();
                title = getString(R.string.title_list);
                break;

            default:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                break;
        }
        Log.d("dit is main", title);

        if (fragment != null) {

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


    }
    void loginCheck(){
        Smartfridge smartfridge = new Smartfridge(this);
        if(!smartfridge.isSignedin()){


            Intent intent = new Intent(MainActivity.this, Login.class);
            MainActivity.this.startActivity(intent);
            finish();
        }
    }

}

