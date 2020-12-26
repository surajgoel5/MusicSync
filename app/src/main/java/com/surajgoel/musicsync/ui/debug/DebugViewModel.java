package com.surajgoel.musicsync.ui.debug;



import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Arrays;

public class DebugViewModel extends ViewModel {

    public MutableLiveData<DebugData> mData;
    private DebugData data;


    public DebugViewModel() {
        mData = new MutableLiveData<>();
        data = new DebugData();
    }


    public static double processPart(int[] m, double fac) {
        int sum = 0;
        for (int i = 0; i < m.length; i++)
            sum += m[i];
        int num = (int) (sum * fac);
        num = num > 255 ? 255 : num;
        return (num);
    }


    public LiveData<DebugData> getData() {
        return (mData);
    }


    public void updateInputs(DebugData data_n) {
        data = data_n;


    }

    public void update(byte[] bytes) {

        byte[] inbytes = Arrays.copyOfRange(bytes, 0, data.input.fullSpectrumLen*2);
        int[] normBytes = new int[(int) data.input.fullSpectrumLen];
        int k = 0; //first elemnt doest have imaginary part
        double mag=0;
        for (int i = 0; i < normBytes.length ; i += 2) {
            byte rfk = inbytes[2 * i];
            byte ifk = inbytes[2 * i + 1];
            mag=rfk*rfk+k*ifk*ifk;
            //int dbval=(int) (10*Math.log10(mag));
            normBytes[i]=(int) Math.pow(mag*data.input.overallMultipler,data.input.normPower);//Math.round(dbval*8*data.input.overallMultipler);
            //normBytes[(int) i / 2] = (int) (data.input.overallMultipler * Math.pow((Math.pow((int) inbytes[i], 2) + k * Math.pow((int) inbytes[i + 1], 2)) / (k + 1), data.input.normPower));
            k = 1;
        }
        long totalNorm=0;
        for(int i: normBytes)
            totalNorm+=i;
        totalNorm+=1;
        //for(int i=0; i< normBytes.length; i++)
         //   normBytes[i]= (int) ( 25*normBytes[i]/totalNorm);

        int[] secBass = Arrays.copyOfRange(normBytes, data.input.bassSectionIdx[0], data.input.bassSectionIdx[1]);
        int[] secMid = Arrays.copyOfRange(normBytes, data.input.midSectionIdx[0], data.input.midSectionIdx[1]);
        int[] secTrebel = Arrays.copyOfRange(normBytes, data.input.trebelSectionIdx[0], data.input.trebelSectionIdx[1]);
        int[][] secs = {secBass, secMid, secTrebel};
        double[] secMultipliers = {data.input.bassMultiplier, data.input.midMultiplier, data.input.trebelMultiplier};

        data.output.colors = new int[]{(int) processPart(secs[0], secMultipliers[0]), (int) processPart(secs[1], secMultipliers[1]), (int) processPart(secs[2], secMultipliers[2])};
        data.output.spectrum = Arrays.copyOfRange(normBytes, data.input.viewableSpectrumIdx[0], data.input.viewableSpectrumIdx[1]);
        mData.setValue(data);
        //mText.setValue(color[0]+"");
        //color = new int[] { (int)mean(secR,1),(int)mean(secR,1),(int)mean(secR,1) };//normBytes2 ;//
            /*if (prevcolor == null){
                prevcolor= new int[color.length];
                Arrays.fill(prevcolor,0);
            }
            for (int i = 0; i < color.length; i++){
                int k=color[i];
                color[i]=2*Math.abs(k-prevcolor[i]/3);
                prevcolor[i]=k;

            }*/

    }

}