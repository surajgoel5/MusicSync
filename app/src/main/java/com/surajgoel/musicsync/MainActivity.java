package com.surajgoel.musicsync;

import android.Manifest;
import android.media.AudioManager;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.google.android.material.navigation.NavigationView;
import com.surajgoel.musicsync.ui.debug.DebugData;
import com.surajgoel.musicsync.ui.debug.DebugViewModel;
import com.surajgoel.musicsync.util.PermissionUtil;

import static android.media.audiofx.Visualizer.SCALING_MODE_AS_PLAYED;

public class MainActivity extends AppCompatActivity {
public DebugData debugData;
    private AppBarConfiguration mAppBarConfiguration;
    private Visualizer mVisualizer;
    private DebugViewModel debugViewModel;

    public boolean debugModeOn=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        debugViewModel=   ViewModelProviders.of(this).get(DebugViewModel.class); //new DebugViewModel();


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_devices, R.id.nav_debug)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        if (PermissionUtil.hasPermission(this, Manifest.permission.RECORD_AUDIO)) {
            initAudio();
        } else {
            PermissionUtil.requestPermission(
                    MainActivity.this, Manifest.permission.RECORD_AUDIO,
                    PermissionUtil.PERMISSION_RESULT_RECORD_AUDIO);
        }
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private void initAudio() {
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setupVisualizerFxAndUi();
        mVisualizer.setEnabled(true);
    }

    private void setupVisualizerFxAndUi() {
        // Create the Visualizer object and attach it to our media player.
        mVisualizer = new Visualizer(0); // Using system audio session ID
        mVisualizer.setEnabled(false);
        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1] );
        mVisualizer.setScalingMode(SCALING_MODE_AS_PLAYED);//SCALING_MODE_NORMALIZED);//
        mVisualizer.setDataCaptureListener(
                new Visualizer.OnDataCaptureListener() {
                    public void onWaveFormDataCapture(
                            Visualizer visualizer,
                            byte[] bytes,
                            int samplingRate) {
                    //Dont need waveform rn
                    }

                    public void onFftDataCapture(
                            Visualizer visualizer,
                            byte[] bytes,
                            int samplingRate) {
                            if(debugModeOn) {
                                debugViewModel.update(bytes);
                            }
                            else{
                             //Work on home fragment, stream to devices

                            }
                    }
                }, Visualizer.getMaxCaptureRate()-1 , false, true);
    }

    public void changeDebugMode(boolean b){
        debugModeOn=b;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVisualizer == null) {
            return;
        }
        mVisualizer.setEnabled(false);
        mVisualizer.release();
    }
}