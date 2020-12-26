package com.surajgoel.musicsync.ui.debug;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.surajgoel.musicsync.MainActivity;
import com.surajgoel.musicsync.R;

import java.util.Arrays;

public class DebugFragment extends Fragment {

    private DebugViewModel debugViewModel;
    private MutableLiveData<String> mText; //debug

    private DebugView debugView ;
    private DebugData debugData=new DebugData();
    private MainActivity mainActivity;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        debugViewModel = ViewModelProviders.of(requireActivity()).get(DebugViewModel.class);
        mainActivity=(MainActivity)  getActivity();//getActivity();
        mainActivity.changeDebugMode(true);
        View root = inflater.inflate(R.layout.fragment_debug, container, false);
        initInputs(root);
        debugView = (DebugView) root.findViewById(R.id.debug_view);


        debugViewModel.getData().observe(getViewLifecycleOwner(), new Observer<DebugData>() {
            @Override
            public void onChanged(@Nullable DebugData data) {
                debugData=data;
                debugView.updateView(debugData);
            }
        });





        return root;
    }

    private  void initInputs(final View root){

         Button updateButton= (Button) root.findViewById(R.id.updateButton);
        final EditText fullLenBox= (EditText) root.findViewById(R.id.fullSpectrumLen);
        final EditText visSpecMinBox= (EditText) root.findViewById(R.id.viewableSpectrumMin);
        final EditText visSpecMaxBox= (EditText) root.findViewById(R.id.viewableSpectrumMax);

        final EditText bassMinBox= (EditText) root.findViewById(R.id.bassIdxmin);
        final EditText bassMaxBox= (EditText) root.findViewById(R.id.bassIdxmax);
        final EditText midMinBox= (EditText) root.findViewById(R.id.midIdxmin);
        final EditText midMaxBox= (EditText) root.findViewById(R.id.midIdxMax);
        final EditText trebelMinBox= (EditText) root.findViewById(R.id.trebelIdxMin);
        final EditText trebelMaxBox= (EditText) root.findViewById(R.id.trebelIdxMax);

        SeekBar overallMultiSeek=(SeekBar) root.findViewById((R.id.overallMultiSeek));
        SeekBar normPowerSeek=(SeekBar) root.findViewById((R.id.normPowerSeek));
        SeekBar bassMultiSeek=(SeekBar) root.findViewById((R.id.bassMultiSeek));
        SeekBar midMultiSeek=(SeekBar) root.findViewById((R.id.midMultiSeek));
        SeekBar trebelMultiSeek=(SeekBar) root.findViewById((R.id.trebelMultiSeek));
        Switch rgbBarSwitch = (Switch) root.findViewById(R.id.rgbBarSwtich);
        Switch prevColorSwitch= (Switch) root.findViewById(R.id.prevColorSwitch);

        updateButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             String bassMinStr=bassMinBox.getText().toString();
             String bassMaxStr=bassMaxBox.getText().toString();

             if (!"".equals(bassMinStr)){
                 debugData.input.bassSectionIdx[0]=Integer.parseInt(bassMinStr); }
             if (!"".equals(bassMaxStr)){
                 debugData.input.bassSectionIdx[1]=Integer.parseInt(bassMaxStr)>debugData.input.bassSectionIdx[0]?Integer.parseInt(bassMaxStr):debugData.input.bassSectionIdx[0]+1;
                 bassMaxBox.setText(debugData.input.bassSectionIdx[1]+"");
             }


             String midMinStr=midMinBox.getText().toString();
             String midMaxStr=midMaxBox.getText().toString();

             if (!"".equals(midMinStr)){
                 debugData.input.midSectionIdx[0]=Integer.parseInt(midMinStr); }
             if (!"".equals(midMaxStr)){
                 debugData.input.midSectionIdx[1]=Integer.parseInt(midMaxStr)>debugData.input.midSectionIdx[0]?Integer.parseInt(midMaxStr):debugData.input.midSectionIdx[0]+1;
                 midMaxBox.setText(debugData.input.midSectionIdx[1]+"");
             }

             String trebelMinStr=trebelMinBox.getText().toString();
             String trebelMaxStr=trebelMaxBox.getText().toString();

             if (!"".equals(trebelMinStr)){
                 debugData.input.trebelSectionIdx[0]=Integer.parseInt(trebelMinStr); }
             if (!"".equals(trebelMaxStr)){
                 debugData.input.trebelSectionIdx[1]=Integer.parseInt(trebelMaxStr)>debugData.input.trebelSectionIdx[0]?Integer.parseInt(trebelMaxStr):debugData.input.trebelSectionIdx[0]+1;
                 trebelMaxBox.setText(debugData.input.trebelSectionIdx[1]+"");
             }


             String visSpecMinStr = visSpecMinBox.getText().toString();
             String visSpecMaxStr = visSpecMaxBox.getText().toString();

             if (!"".equals(visSpecMinStr)){
                 debugData.input.viewableSpectrumIdx[0]=Integer.parseInt(visSpecMinStr); }
             if (!"".equals(visSpecMaxStr)){
                 debugData.input.viewableSpectrumIdx[1]=Integer.parseInt(visSpecMaxStr)>debugData.input.viewableSpectrumIdx[0]?Integer.parseInt(visSpecMaxStr):debugData.input.viewableSpectrumIdx[0]+1;
                 visSpecMaxBox.setText(debugData.input.viewableSpectrumIdx[1]+"");
             }

             String fullLenStr= fullLenBox.getText().toString();
             if (!"".equals(fullLenStr)) {
                 int fullLen = Integer.parseInt(fullLenStr);
                 int[] maxes = {fullLen, debugData.input.bassSectionIdx[1], debugData.input.midSectionIdx[1], debugData.input.trebelSectionIdx[1]};
                 Arrays.sort(maxes);
                 debugData.input.fullSpectrumLen = maxes[3];
                 fullLenBox.setText(debugData.input.fullSpectrumLen + "");
             }
debugViewModel.updateInputs(debugData);
         }
     });


     normPowerSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
         @Override
         public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
             debugData.input.normPower=((double) i)/100;
             debugViewModel.updateInputs(debugData);
             ((TextView) root.findViewById(R.id.normMultiText)).setText("Norm Power:"+debugData.input.normPower);
         }

         @Override
         public void onStartTrackingTouch(SeekBar seekBar) {

         }

         @Override
         public void onStopTrackingTouch(SeekBar seekBar) {

         }
     });

     overallMultiSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
         @Override
         public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
             debugData.input.overallMultipler= ((double) i)/10;

             debugViewModel.updateInputs(debugData);
             ((TextView) root.findViewById(R.id.overallMultiText)).setText("Overall Multiplier:"+debugData.input.overallMultipler);
         }

         @Override
         public void onStartTrackingTouch(SeekBar seekBar) {

         }

         @Override
         public void onStopTrackingTouch(SeekBar seekBar) {

         }
     });

     bassMultiSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
         @Override
         public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
             debugData.input.bassMultiplier= ((double) i)/100;

             debugViewModel.updateInputs(debugData);
             ((TextView) root.findViewById(R.id.bassMultiText)).setText("Bass:"+debugData.input.bassMultiplier);
         }

         @Override
         public void onStartTrackingTouch(SeekBar seekBar) {

         }

         @Override
         public void onStopTrackingTouch(SeekBar seekBar) {

         }
     });


        midMultiSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                debugData.input.midMultiplier= ((double) i)/100;

                debugViewModel.updateInputs(debugData);
                ((TextView) root.findViewById(R.id.midMultiText)).setText("Mid:"+debugData.input.midMultiplier);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        trebelMultiSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                debugData.input.trebelMultiplier= ((double) i)/100;

                debugViewModel.updateInputs(debugData);
                ((TextView) root.findViewById(R.id.trebelMultiText)).setText("Treble:"+debugData.input.trebelMultiplier);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    rgbBarSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            debugData.output.showColorBar=b;
        }
    });

    prevColorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            debugData.output.showFinalColor=b;
        }
    });

    }


    @Override
    public void onResume() {
        super.onResume();
        mainActivity.changeDebugMode(true);

    }

    @Override
    public void onStart() {
        super.onStart();
        mainActivity.changeDebugMode(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
       mainActivity.changeDebugMode(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainActivity.changeDebugMode(false);
        Log.e("LOL","MY BOY DESTOYEDD");
    }

    @Override
    public void onStop() {
        super.onStop();
        mainActivity.changeDebugMode(false);
        Log.e("LOL","MY BOY STOPPED");
    }
}

