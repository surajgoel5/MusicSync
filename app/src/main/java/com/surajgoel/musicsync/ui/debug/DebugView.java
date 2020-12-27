package com.surajgoel.musicsync.ui.debug;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.surajgoel.musicsync.R;

public class DebugView extends View {


    private Paint specPaint = new Paint();
    private Paint[] rgbPaint = new Paint[3];
    private DebugData mData = new DebugData();
    private Paint finalMixPaint = new Paint();



    public DebugView(Context context) {
        super(context);
        init();
    }

    public DebugView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DebugView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        specPaint.setStrokeWidth(3.0f);
        specPaint.setAntiAlias(true);
        specPaint.setStyle(Paint.Style.STROKE);
        specPaint.setStrokeJoin(Paint.Join.ROUND);
        specPaint.setStrokeCap(Paint.Cap.ROUND);
        specPaint.setPathEffect(new CornerPathEffect(10.0f));
        specPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));

        finalMixPaint.setStyle(Paint.Style.FILL);
        int[] colors = {R.color.redBar, R.color.greenBar, R.color.blueBar};
        for (int i = 0; i < 3; i++) {
            rgbPaint[i] = new Paint();
            rgbPaint[i].setStrokeWidth(3.0f);
            rgbPaint[i].setAntiAlias(true);
            rgbPaint[i].setStyle(Paint.Style.FILL);
            rgbPaint[i].setColor(ContextCompat.getColor(getContext(), colors[i]));

        }

    }

    public void updateView(DebugData data) {
        mData = data;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        if (mData.output.spectrum == null) {
            return;
        }
        int lenspec = mData.output.spectrum.length;
        for (int i = 0; i < lenspec; i++) {

            canvas.drawRect(getWidth() * (i) / lenspec, getHeight(), getWidth() * (i + 1) / lenspec, ((255 - mData.output.spectrum[i])) * (getHeight()) / 255, specPaint);

        }
        if (mData.output.showColorBar) {
            canvas.drawRect(getWidth() * (mData.input.bassSectionIdx[0]) / lenspec, getHeight(), getWidth() * (mData.input.bassSectionIdx[1]) / lenspec, ((255 - mData.output.colors[0])) * (getHeight()) / 255, rgbPaint[0]);
            canvas.drawRect(getWidth() * (mData.input.midSectionIdx[0]) / lenspec, getHeight(), getWidth() * (mData.input.midSectionIdx[1]) / lenspec, ((255 - mData.output.colors[1])) * (getHeight()) / 255, rgbPaint[1]);
            canvas.drawRect(getWidth() * (mData.input.trebelSectionIdx[0]) / lenspec, getHeight(), getWidth() * (mData.input.trebelSectionIdx[1]) / lenspec, ((255 - mData.output.colors[2])) * (getHeight()) / 255, rgbPaint[2]);
        }
        if (mData.output.showFinalColor) {
            int r = mData.output.colors[0];
            int g = mData.output.colors[1];
            int b = mData.output.colors[2];
            int max = Math.max(r, (Math.max(g, b)));
            if (max != 0) {
                float alpha = max / 255f;
                r = (int) (r * (1 / alpha));
                g = (int) (g * (1 / alpha));
                b = (int) (b * (1 / alpha));

            }
            finalMixPaint.setColor(Color.argb(max, r, g, b));
            canvas.drawCircle((float) (getWidth() * 0.85), (float) (getHeight() * 0.2), getWidth() / 10, finalMixPaint);



        }


    }

}
