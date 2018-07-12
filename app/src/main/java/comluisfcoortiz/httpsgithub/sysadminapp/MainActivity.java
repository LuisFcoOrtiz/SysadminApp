package comluisfcoortiz.httpsgithub.sysadminapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import comluisfcoortiz.httpsgithub.sysadminapp.settings.Settings;
import comluisfcoortiz.httpsgithub.sysadminapp.utilities.SshConector;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //settings and variables
    SharedPreferences settings;
    boolean autoConnection;

    ProgressDialog progressDialog;

    SshConector sshConector = new SshConector("192.168.1.100","luis","luis");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        getSettings();
        //AUTOMATIC connection to server
        if(autoConnection) {
            new ThreadAsyn().execute();     //prepara la conexion
        }else {
            Toast.makeText(getApplicationContext(),  R.string.autoMessageNO, Toast.LENGTH_LONG).show();
        }

        //floating button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //top right menu
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            setSettings();      //open activity view
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            new ThreadAsyn().execute();     //prepara la conexion
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //open settings view
    public void setSettings() {
        Intent i = new Intent(this,Settings.class);
        startActivity(i);
    }

    //set the variables from the settings
    public void getSettings() {
        autoConnection = settings.getBoolean("automaticConnection",false);

    }


    //asyntask
    class ThreadAsyn extends AsyncTask<String,Integer,Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {

            if(sshConector.connect()) {
                Log.i("INFO_CONEXION","CONECTADOOOO");
                return true;
            }else {
                Log.i("INFO_CONEXION","IMPOSIBLE CONECTAR");
                return false;
            }   //connect with the server
        }//start the task

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(getApplicationContext().getResources().getString(R.string.connectingServerMSG));
            progressDialog.setCancelable(false);
            progressDialog.show();

            super.onPreExecute();
        }//before the task


        @Override
        protected void onPostExecute(Boolean resultConnection) {

            if(resultConnection) {
                Toast.makeText(getApplicationContext(), R.string.connectionOK, Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getApplicationContext(), R.string.connectionNO, Toast.LENGTH_LONG).show();
            }
            progressDialog.dismiss();       //end progress dialog
            super.onPostExecute(resultConnection);

        }//end of task


    }//end asyntask

}//end of MainActivity
