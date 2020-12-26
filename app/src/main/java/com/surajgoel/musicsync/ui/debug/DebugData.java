package com.surajgoel.musicsync.ui.debug;

public class DebugData {
    public DebugData() {
        this.input = new Input();
        this.output = new Output();
    }

    public class Input{
        public int fullSpectrumLen=1000; //Full sectrum Data to process
        public int[] viewableSpectrumIdx= {0,100}; //index pointers to start and end viewable section
        public int[] bassSectionIdx= {0,8};
        public int[] midSectionIdx= {8,19};
        public int[] trebelSectionIdx= {19,30};
        public double bassMultiplier=1;
        public double midMultiplier=1;
        public double trebelMultiplier=1;
        public double overallMultipler=1;
        public double normPower=0.5;

    }
    public class Output{
        public int[] spectrum;
        public int[] colors=new int[3];
        public boolean showColorBar=true;

        public boolean showFinalColor=true;


        public Output() {
            this.spectrum = null;
        }
    }

    public Input input;
    public Output output;


}
